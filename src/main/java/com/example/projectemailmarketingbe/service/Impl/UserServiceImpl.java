package com.example.projectemailmarketingbe.service.Impl;

import com.example.projectemailmarketingbe.dto.UserLoginDto;
import com.example.projectemailmarketingbe.dto.UserLoginRpDto;
import com.example.projectemailmarketingbe.dto.UserRegisterDto;
import com.example.projectemailmarketingbe.dto.UserRegisterRpDto;
import com.example.projectemailmarketingbe.exception.BadRequestException;
import com.example.projectemailmarketingbe.exception.NotFoundException;
import com.example.projectemailmarketingbe.model.UserEntity;
import com.example.projectemailmarketingbe.repository.UserRepository;
import com.example.projectemailmarketingbe.service.UserService;
import com.example.projectemailmarketingbe.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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

    @Override
    public UserRegisterRpDto register(UserRegisterDto userRegisterRequestDto) {
        userRepository.findByUsername(userRegisterRequestDto.getUsername()).ifPresent((user) ->{
            throw new BadRequestException(String.format(USER_EXISTED, userRegisterRequestDto.getUsername()));
        });
        UserEntity user = modelMapper.map(userRegisterRequestDto, UserEntity.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
            loginResponseDto.setAccessToken(jwtUtils.generateAccessToken(user.getUsername()));
            loginResponseDto.setRefreshToken(jwtUtils.generateRefreshToken(user.getUsername()));
            loginResponseDto.setExpiredDateAccess(LocalDateTime.now().plusMinutes(40));
            loginResponseDto.setExpiredDateRefresh(LocalDateTime.now().plusDays(1));
            return loginResponseDto;
        } else {
            throw new NotFoundException("Username or Password does not correct");
        }
    }
}
