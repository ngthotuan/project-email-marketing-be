package com.example.projectemailmarketingbe.service;

import com.example.projectemailmarketingbe.dto.*;

public interface UserService {
    UserRegisterRpDto register(UserRegisterDto userRegisterRequestDto);

    UserLoginRpDto login(UserLoginDto userLoginRequestDto);

    UserInfoRpDto getUserInfo(String username);
}
