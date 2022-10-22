package com.example.projectemailmarketingbe.mapper;

import com.example.projectemailmarketingbe.dto.ProxyDto;
import com.example.projectemailmarketingbe.dto.ProxyRpDto;
import com.example.projectemailmarketingbe.model.ProxyEntity;

public interface ProxyMapper {
    ProxyRpDto proxyEntityToProxyRpDto(ProxyEntity proxyEntity);

    ProxyEntity proxyDtoToProxyEntity(ProxyDto proxyDto);
}
