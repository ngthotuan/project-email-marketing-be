package com.example.projectemailmarketingbe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailWithProxyDto {
    private String email;
    private String passwordEmail;
    private String proxyName;
    private String username;
    private String passwordProxy;
    private String host;
    private String port;
    private String type;

}
