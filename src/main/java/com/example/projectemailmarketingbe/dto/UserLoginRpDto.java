package com.example.projectemailmarketingbe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRpDto {
    private UUID userId;
    private String username;
    private String name;
    private String accessToken;
    private String refreshToken;
    private long accessTokenExpiredAt;
    private long refreshTokenExpiredAt;
}
