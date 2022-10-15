package com.example.projectemailmarketingbe.service;

import com.example.projectemailmarketingbe.dto.EmailDto;
import com.example.projectemailmarketingbe.dto.EmailRpDto;
import com.example.projectemailmarketingbe.dto.EmailWithProxyDto;
import com.example.projectemailmarketingbe.dto.PageResponseDto;

import java.util.List;
import java.util.UUID;

public interface EmailService {
    PageResponseDto<EmailRpDto> findAll(String search, int page, int size);

    List<EmailRpDto> findAll();

    EmailRpDto findByEmail(String email);

    EmailRpDto addNewEmail(EmailDto emailDto);

    void deleteEmail(String email);

    EmailRpDto updateEmail(EmailDto emailDto);

    List<EmailRpDto> addEmailsAndProxy(List<EmailWithProxyDto> emailWithProxyDtos);
}
