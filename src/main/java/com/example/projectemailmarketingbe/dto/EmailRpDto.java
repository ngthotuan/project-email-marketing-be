package com.example.projectemailmarketingbe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailRpDto {
    private String email;
    private String password;
    private String emailName;
    private Long proxyId;
    private String proxyName;
    private String proxyUsername;
    private String proxyPassword;
    private String proxyHost;
    private String proxyPort;
}
