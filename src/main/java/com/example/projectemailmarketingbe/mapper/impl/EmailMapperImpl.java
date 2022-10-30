package com.example.projectemailmarketingbe.mapper.impl;

import com.example.projectemailmarketingbe.dto.EmailRpDto;
import com.example.projectemailmarketingbe.mapper.EmailMapper;
import com.example.projectemailmarketingbe.model.EmailEntity;
import com.example.projectemailmarketingbe.model.ProxyEntity;
import org.springframework.stereotype.Component;

@Component
public class EmailMapperImpl implements EmailMapper {
    @Override
    public EmailRpDto emailToEmailDto(EmailEntity emailEntity) {
        ProxyEntity proxyEntity = emailEntity.getProxyEntity();
        return EmailRpDto.builder()
                .email(emailEntity.getEmail())
                .password(emailEntity.getPassword())
                .emailName(emailEntity.getEmailName())
                .proxyId(proxyEntity != null ? proxyEntity.getProxyId() : null)
                .proxyName(proxyEntity != null ? proxyEntity.getName() : "")
                .proxyUsername(proxyEntity != null ? proxyEntity.getUsername() : "")
                .proxyPassword(proxyEntity != null ? proxyEntity.getPassword() : "")
                .proxyHost(proxyEntity != null ? proxyEntity.getHost() : "")
                .proxyPort(proxyEntity != null ? proxyEntity.getPort() : "")
                .build();
    }
}
