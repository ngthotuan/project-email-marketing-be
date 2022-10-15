package com.example.projectemailmarketingbe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProxyRpDto {
    private Long Id;
    private String name;
    private String username;
    private String password;
    private String host;
    private String port;
    private String type;
}
