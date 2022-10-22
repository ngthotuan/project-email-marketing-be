package com.example.projectemailmarketingbe.mapper.impl;

import com.example.projectemailmarketingbe.dto.ProxyDto;
import com.example.projectemailmarketingbe.dto.ProxyRpDto;
import com.example.projectemailmarketingbe.mapper.ProxyMapper;
import com.example.projectemailmarketingbe.model.ProxyEntity;
import org.springframework.stereotype.Component;

@Component
public class ProxyMapperImpl implements ProxyMapper {
    @Override
    public ProxyRpDto proxyEntityToProxyRpDto(ProxyEntity proxyEntity) {
        return ProxyRpDto.builder()
                .id(proxyEntity.getProxyId())
                .password(proxyEntity.getPassword())
                .host(proxyEntity.getHost())
                .port(proxyEntity.getPort())
                .username(proxyEntity.getUsername())
                .name(proxyEntity.getName())
                .build();
    }

    @Override
    public ProxyEntity proxyDtoToProxyEntity(ProxyDto proxyDto) {
        return ProxyEntity.builder()
                .host(proxyDto.getHost())
                .port(proxyDto.getPort())
                .password(proxyDto.getPassword())
                .name(proxyDto.getName())
                .username(proxyDto.getUsername())
                .build();
    }

}
 
 
 
