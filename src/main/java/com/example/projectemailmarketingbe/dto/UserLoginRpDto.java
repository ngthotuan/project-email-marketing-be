package com.example.projectemailmarketingbe.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserLoginRpDto {
    private UUID userUuid;
    private String username;
    private String name;
    private String accessToken;
    private String refreshToken;
}
