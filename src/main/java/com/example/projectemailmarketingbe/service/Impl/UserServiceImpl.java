package com.example.projectemailmarketingbe.service.Impl;

import com.example.projectemailmarketingbe.dto.*;
import com.example.projectemailmarketingbe.exception.BadRequestException;
import com.example.projectemailmarketingbe.exception.NotFoundException;
import com.example.projectemailmarketingbe.model.UserEntity;
import com.example.projectemailmarketingbe.repository.UserRepository;
import com.example.projectemailmarketingbe.service.UserService;
import com.example.projectemailmarketingbe.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.projectemailmarketingbe.constant.MessageConstant.USER_EXISTED;
import static com.example.projectemailmarketingbe.constant.MessageConstant.USER_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    @Value("${com.example.demo.jwt.secret}")
    private String secretKeyParam;

    @Override
    public UserRegisterRpDto register(UserRegisterDto userRegisterRequestDto) {
        if (!validateKey(userRegisterRequestDto.getSecretKey()))
            throw new NotFoundException("secret key is incorrect!");
        userRepository.findByUsername(userRegisterRequestDto.getUsername()).ifPresent((user) -> {
            throw new BadRequestException(String.format(USER_EXISTED, userRegisterRequestDto.getUsername()));
        });
        UserEntity user = modelMapper.map(userRegisterRequestDto, UserEntity.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ADMIN");
        UserEntity userSaved = userRepository.save(user);
        return modelMapper.map(userSaved, UserRegisterRpDto.class);
    }

    @Override
    public UserLoginRpDto login(UserLoginDto userLoginRequestDto) {
        UserEntity user = userRepository.findByUsername(userLoginRequestDto.getUsername())
                .orElseThrow(() -> {
                    log.error(USER_NOT_FOUND, userLoginRequestDto.getUsername());
                    return new NotFoundException("user not found");
                });
        if (passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())) {
            UserLoginRpDto loginResponseDto = modelMapper.map(user, UserLoginRpDto.class);
            String accessToken = jwtUtils.generateAccessToken(user.getUsername());
            String refreshToken = jwtUtils.generateRefreshToken(user.getUsername());
            loginResponseDto.setAccessToken(accessToken);
            loginResponseDto.setRefreshToken(refreshToken);
            loginResponseDto.setAccessTokenExpiredAt(jwtUtils.getExpirationFromToken(accessToken));
            loginResponseDto.setRefreshTokenExpiredAt(jwtUtils.getExpirationFromToken(refreshToken));
            return loginResponseDto;
        } else {
            throw new NotFoundException("Username or Password does not correct");
        }
    }

    @Override
    public UserInfoRpDto getUserInfo(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error(USER_NOT_FOUND, username);
                    return new NotFoundException("user not found");
                });
        UserInfoRpDto userInfoRpDto = modelMapper.map(user, UserInfoRpDto.class);
        userInfoRpDto.setRole(user.getRole());
        return userInfoRpDto;
    }

    private Boolean validateKey(String secretKey) {
        return secretKey.equals(secretKeyParam);
    }
}
