package com.example.projectemailmarketingbe.service.Impl;

import com.example.projectemailmarketingbe.dto.EmailDto;
import com.example.projectemailmarketingbe.dto.EmailRpDto;
import com.example.projectemailmarketingbe.dto.EmailWithProxyDto;
import com.example.projectemailmarketingbe.dto.PageResponseDto;
import com.example.projectemailmarketingbe.exception.NotFoundException;
import com.example.projectemailmarketingbe.model.EmailEntity;
import com.example.projectemailmarketingbe.model.ProxyEntity;
import com.example.projectemailmarketingbe.repository.EmailRepository;
import com.example.projectemailmarketingbe.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final EmailRepository emailRepository;
    private final ModelMapper modelMapper;

    @Override
    public PageResponseDto<EmailRpDto> findAll(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EmailEntity> pageEmail = search.isBlank()
                ? emailRepository.findAll(pageable)
                : emailRepository.findAllByEmailContaining(search, pageable);
        PageResponseDto pageResponseDto = PageResponseDto
                .builder()
                .page(page)
                .size(size)
                .totalPages(pageEmail.getTotalPages())
                .totalElements(pageEmail.getTotalElements())
                .elements(pageEmail.stream()
                        .map(emailEntity -> EmailRpDto.builder().email(emailEntity.getEmail()).password(emailEntity.getPassword()).build())
                        .collect(Collectors.toList()))
                .build();
        return pageResponseDto;
    }

    @Override
    public EmailRpDto findByEmail(String email) {
        EmailEntity emailInDb = emailRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(String.format("%s not found", email)));
        return modelMapper.map(emailInDb, EmailRpDto.class);
    }

    @Override
    public EmailRpDto addNewEmail(EmailDto email) {
        EmailEntity save = emailRepository.save(EmailEntity.builder().email(email.getEmail()).password(email.getPassword()).build());
        return modelMapper.map(save, EmailRpDto.class);
    }

    @Override
    public void deleteEmail(String email) {
        EmailEntity emailInDb = emailRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(String.format("%s not found", email)));
        emailRepository.delete(emailInDb);
    }

    @Override
    @Transactional
    public EmailRpDto updateEmail(EmailDto email) {
        EmailEntity emailInDb = emailRepository.findByEmail(email.getEmail())
                .orElseThrow(() -> new NotFoundException(String.format("%s not found", email)));
        emailInDb.setEmail(email.getEmail());
        emailInDb.setPassword(email.getPassword());
        return modelMapper.map(emailInDb, EmailRpDto.class);
    }

    @Override
    @Transactional
    public List<EmailRpDto> addEmailsAndProxy(List<EmailWithProxyDto> emailWithProxyDtos) {
        List<EmailEntity> result = new ArrayList<>();
        List<EmailEntity> emailEntities = emailWithProxyDtos.stream().map(emailWithProxyDto -> EmailEntity.builder()
                .email(emailWithProxyDto.getEmail())
                .password(emailWithProxyDto.getPasswordEmail())
                .proxyEntity(ProxyEntity.builder()
                        .host(emailWithProxyDto.getHost())
                        .password(emailWithProxyDto.getPasswordProxy())
                        .port(emailWithProxyDto.getPort())
                        .username(emailWithProxyDto.getUsername())
                        .type(emailWithProxyDto.getType()).build()

                ).build()).collect(Collectors.toList());
        for (EmailEntity emailEntity : emailEntities) {
            EmailEntity save = emailRepository.save(emailEntity);
            result.add(save);
        }
        return result.stream().map(e -> EmailRpDto.builder().email(e.getEmail()).build()).collect(Collectors.toList());
    }


}
