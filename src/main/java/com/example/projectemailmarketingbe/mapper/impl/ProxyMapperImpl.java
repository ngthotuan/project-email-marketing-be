package com.example.projectemailmarketingbe.mapper.impl;

import com.example.projectemailmarketingbe.dto.ProxyRpDto;
import com.example.projectemailmarketingbe.mapper.ProxyMapper;
import com.example.projectemailmarketingbe.model.ProxyEntity;
import org.springframework.stereotype.Component;

@Component
public class ProxyMapperImpl implements ProxyMapper {
    @Override
    public ProxyRpDto proxyEntityToProxyRpDto(ProxyEntity proxyEntity) {
        return ProxyRpDto.builder()
                .Id(proxyEntity.getProxyId())
                .host(proxyEntity.getHost())
                .port(proxyEntity.getPort())
                .username(proxyEntity.getUsername())
                .name(proxyEntity.getName())
                .build();
    }

    @Override
    public ProxyEntity proxyRpDtoToProxyEntity(ProxyRpDto proxyRpDto) {
        return ProxyEntity.builder()
                .host(proxyRpDto.getHost())
                .port(proxyRpDto.getPassword())
                .password(proxyRpDto.getPassword())
                .name(proxyRpDto.getName())
                .username(proxyRpDto.getUsername())
                .build();
    }

}
 
 
 
