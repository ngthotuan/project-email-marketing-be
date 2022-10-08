package com.example.projectemailmarketingbe.service.Impl;

import com.example.projectemailmarketingbe.dto.UserLoginDto;
import com.example.projectemailmarketingbe.dto.UserLoginRpDto;
import com.example.projectemailmarketingbe.dto.UserRegisterDto;
import com.example.projectemailmarketingbe.dto.UserRegisterRpDto;
import com.example.projectemailmarketingbe.exception.NotFoundException;
import com.example.projectemailmarketingbe.model.UserEntity;
import com.example.projectemailmarketingbe.repository.UserRepository;
import com.example.projectemailmarketingbe.service.UserService;
import com.example.projectemailmarketingbe.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    @Override
    public UserRegisterRpDto register(UserRegisterDto userRegisterRequestDto) {
        UserEntity user = modelMapper.map(userRegisterRequestDto, UserEntity.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity userSaved = userRepository.save(user);
        UserRegisterRpDto userRegisterResponseDto = modelMapper.map(userSaved, UserRegisterRpDto.class);
        return userRegisterResponseDto;
    }

    @Override
    public UserLoginRpDto login(UserLoginDto userLoginRequestDto) {
        UserEntity user = userRepository.findByUsername(userLoginRequestDto.getUsername()).orElseThrow(()-> new NotFoundException("user not found"));
//        if (!user.getIsActive()) {
//            throw new UserBlockedException("User is not active");
//        }
//        if (user.getIsDeleted()) {
//            throw new UserBlockedException("User is deleted");
//        }
        if (passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())) {
            UserLoginRpDto loginResponseDto = modelMapper.map(user, UserLoginRpDto.class);
            loginResponseDto.setAccessToken(jwtUtils.generateAccessToken(user.getUsername()));
            loginResponseDto.setRefreshToken(jwtUtils.generateRefreshToken(user.getUsername()));
            return loginResponseDto;
        } else {
            throw new NotFoundException("Username or Password does not correct");
        }
    }
}
