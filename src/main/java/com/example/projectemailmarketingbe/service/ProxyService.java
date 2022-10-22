package com.example.projectemailmarketingbe.service;

import com.example.projectemailmarketingbe.dto.PageResponseDto;
import com.example.projectemailmarketingbe.dto.ProxyDto;
import com.example.projectemailmarketingbe.dto.ProxyRpDto;
import com.example.projectemailmarketingbe.model.ProxyEntity;

import java.util.List;

public interface ProxyService {
    List<ProxyRpDto> findAllProxy();

    PageResponseDto<ProxyRpDto> findAllProxyWithPagingAndSearch(String search, int page, int size);

    List<ProxyRpDto> addListProxies(List<ProxyDto> proxyDtos);

    ProxyRpDto updateProxy(ProxyRpDto proxyRpDto);

    void deleteProxyById(Long proxyId);

    ProxyRpDto findProxyById(Long proxyId);

    ProxyRpDto addProxy(ProxyDto proxyDto);

    ProxyEntity findProxyByIdReturnEntity(Long proxyId);

}
