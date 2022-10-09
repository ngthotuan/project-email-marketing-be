package com.example.projectemailmarketingbe.mapper.impl;

import com.example.projectemailmarketingbe.dto.EmailRpDto;
import com.example.projectemailmarketingbe.mapper.EmailMapper;
import com.example.projectemailmarketingbe.model.EmailEntity;
import org.springframework.stereotype.Component;

@Component
public class EmailMapperImpl implements EmailMapper {
    @Override
    public EmailRpDto emailToEmailDto(EmailEntity emailEntity) {
        return EmailRpDto.builder()
                .proxyUsername(emailEntity.getProxyEntity().getUsername())
                .email(emailEntity.getEmail())
                .password(emailEntity.getPassword())
                .proxyName(emailEntity.getProxyEntity().getName())
                .host(emailEntity.getProxyEntity().getHost())
                .port(emailEntity.getProxyEntity().getPort())
                .type(emailEntity.getProxyEntity().getType())
                .build();
    }
}
