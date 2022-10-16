package com.example.projectemailmarketingbe.service;

import com.example.projectemailmarketingbe.dto.PageResponseDto;
import com.example.projectemailmarketingbe.dto.ProxyRpDto;
import com.example.projectemailmarketingbe.dto.ProxyUpdateDto;
import com.example.projectemailmarketingbe.model.ProxyEntity;

import java.util.List;

public interface ProxyService {
    List<ProxyRpDto> findAllProxy();

    PageResponseDto<ProxyRpDto> findAllProxyWithPagingAndSearch(String search, int page, int size);

    List<ProxyRpDto> addListProxies(List<ProxyRpDto> proxyRpDtos);

    ProxyRpDto updateProxy(ProxyUpdateDto proxyUpdateDto);

    void deleteProxyById(Long proxyId);

    ProxyRpDto findProxyById(Long proxyId);

    ProxyRpDto addProxy(ProxyRpDto proxyRpDto);

    ProxyEntity findProxyByIdReturnEntity(Long proxyId);

}
