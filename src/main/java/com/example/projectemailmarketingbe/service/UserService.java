package com.example.projectemailmarketingbe.service;

import com.example.projectemailmarketingbe.dto.UserLoginDto;
import com.example.projectemailmarketingbe.dto.UserLoginRpDto;
import com.example.projectemailmarketingbe.dto.UserRegisterDto;
import com.example.projectemailmarketingbe.dto.UserRegisterRpDto;

public interface UserService {
    UserRegisterRpDto register(UserRegisterDto userRegisterRequestDto);
    UserLoginRpDto login(UserLoginDto userLoginRequestDto);
}
