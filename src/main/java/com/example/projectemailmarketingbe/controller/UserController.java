package com.example.projectemailmarketingbe.controller;

import com.example.projectemailmarketingbe.configuration.security.UserDetailService;
import com.example.projectemailmarketingbe.dto.*;
import com.example.projectemailmarketingbe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<ResponseBodyDto<UserRegisterRpDto>> createUser(@Validated @RequestBody UserRegisterDto userRegisterRequestDto) {
        ResponseBodyDto<UserRegisterRpDto> responseBodyDto = new ResponseBodyDto<UserRegisterRpDto>();
        responseBodyDto.setData(userService.register(userRegisterRequestDto));
        responseBodyDto.setStatusCode(200);
        return ResponseEntity.ok(responseBodyDto);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseBodyDto<UserLoginRpDto>> login(@Validated @RequestBody UserLoginDto requestDto) {
        ResponseBodyDto<UserLoginRpDto> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(userService.login(requestDto));
        responseBodyDto.setStatusCode(201);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBodyDto);
    }

    @GetMapping("/info")
    public ResponseEntity<ResponseBodyDto<UserInfoRpDto>> getUserInfo(@AuthenticationPrincipal UserDetailService userDetails) {
        ResponseBodyDto<UserInfoRpDto> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(userService.getUserInfo(userDetails.getUsername()));
        responseBodyDto.setStatusCode(200);
        return ResponseEntity.ok(responseBodyDto);
    }


}
