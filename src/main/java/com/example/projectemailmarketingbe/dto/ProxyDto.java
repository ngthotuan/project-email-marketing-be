package com.example.projectemailmarketingbe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProxyDto {
    private String name;
    private String username;
    private String password;
    private String host;
    private String port;
}
