package com.example.projectemailmarketingbe.controller;

import com.example.projectemailmarketingbe.dto.ResponseBodyDto;
import com.example.projectemailmarketingbe.dto.UserRegisterDto;
import com.example.projectemailmarketingbe.dto.UserRegisterRpDto;
import com.example.projectemailmarketingbe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<ResponseBodyDto<UserRegisterRpDto>> createUser(@RequestBody UserRegisterDto userRegisterRequestDto) {
        ResponseBodyDto<UserRegisterRpDto> responseBodyDto = new ResponseBodyDto<UserRegisterRpDto>();
        responseBodyDto.setData(userService.register(userRegisterRequestDto));
        responseBodyDto.setStatusCode(200);
        return ResponseEntity.ok(responseBodyDto);
    }
}
