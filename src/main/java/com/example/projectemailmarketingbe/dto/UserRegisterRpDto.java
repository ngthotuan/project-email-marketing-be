package com.example.projectemailmarketingbe.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserRegisterRpDto {
    private String username;
    private String email;
    private String name;
}
