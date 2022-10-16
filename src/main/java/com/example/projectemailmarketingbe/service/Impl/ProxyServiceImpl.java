package com.example.projectemailmarketingbe.service.Impl;

import com.example.projectemailmarketingbe.dto.PageResponseDto;
import com.example.projectemailmarketingbe.dto.ProxyRpDto;
import com.example.projectemailmarketingbe.dto.ProxyUpdateDto;
import com.example.projectemailmarketingbe.exception.NotFoundException;
import com.example.projectemailmarketingbe.mapper.ProxyMapper;
import com.example.projectemailmarketingbe.model.ProxyEntity;
import com.example.projectemailmarketingbe.repository.ProxyRepository;
import com.example.projectemailmarketingbe.service.ProxyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.projectemailmarketingbe.constant.MessageConstant.PROXY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProxyServiceImpl implements ProxyService {
    private final ProxyRepository proxyRepository;
    private final ProxyMapper proxyMapper;

    @Override
    public List<ProxyRpDto> findAllProxy() {
        return proxyRepository.findAll().stream().map(proxyMapper::proxyEntityToProxyRpDto).collect(Collectors.toList());
    }

    @Override
    public PageResponseDto<ProxyRpDto> findAllProxyWithPagingAndSearch(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProxyEntity> pageEmail = search.isBlank()
                ? proxyRepository.findAll(pageable)
                : proxyRepository.findAll(search, pageable);
        PageResponseDto pageResponseDto = PageResponseDto
                .builder()
                .page(page)
                .size(size)
                .totalPages(pageEmail.getTotalPages())
                .totalElements(pageEmail.getTotalElements())
                .elements(pageEmail.stream()
                        .map(proxyMapper::proxyEntityToProxyRpDto)
                        .collect(Collectors.toList()))
                .build();
        return pageResponseDto;
    }

    @Override
    public List<ProxyRpDto> addListProxies(List<ProxyRpDto> proxyRpDtos) {
        List<ProxyEntity> proxyEntities =
                proxyRepository.saveAll(proxyRpDtos.stream().map(proxyMapper::proxyRpDtoToProxyEntity).collect(Collectors.toList()));
        return proxyEntities.stream().map(proxyMapper::proxyEntityToProxyRpDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProxyRpDto updateProxy(ProxyUpdateDto proxyUpdateDto) {
        ProxyEntity proxyByIdReturnEntity = findProxyByIdReturnEntity(proxyUpdateDto.getId());
        proxyByIdReturnEntity.setHost(proxyUpdateDto.getHost());
        proxyByIdReturnEntity.setPassword(proxyUpdateDto.getPassword());
        proxyByIdReturnEntity.setName(proxyUpdateDto.getName());
        proxyByIdReturnEntity.setPort(proxyUpdateDto.getPort());
        proxyByIdReturnEntity.setUsername(proxyUpdateDto.getUsername());


        return proxyMapper.proxyEntityToProxyRpDto(proxyByIdReturnEntity);
    }

    @Override
    public void deleteProxyById(Long proxyId) {
        var proxyByIdReturnEntity = findProxyByIdReturnEntity(proxyId);
        proxyRepository.delete(proxyByIdReturnEntity);
    }

    @Override
    public ProxyRpDto findProxyById(Long proxyId) {
        ProxyEntity proxyEntity = findProxyByIdReturnEntity(proxyId);
        return proxyMapper.proxyEntityToProxyRpDto(proxyEntity);
    }

    @Override
    public ProxyRpDto addProxy(ProxyRpDto proxyRpDto) {
        ProxyEntity proxyEntity = proxyRepository.save(proxyMapper.proxyRpDtoToProxyEntity(proxyRpDto));
        return proxyMapper.proxyEntityToProxyRpDto(proxyEntity);
    }

    private ProxyEntity findProxyByIdReturnEntity(Long proxyId) {
        return proxyRepository.findById(proxyId).orElseThrow(
                () -> new NotFoundException(PROXY_NOT_FOUND));
    }
}
