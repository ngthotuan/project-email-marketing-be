package com.example.projectemailmarketingbe.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserLoginDto {
    private String username;
    private String password;
}
