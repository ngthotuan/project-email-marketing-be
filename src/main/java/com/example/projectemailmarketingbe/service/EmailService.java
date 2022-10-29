package com.example.projectemailmarketingbe.service;

import com.example.projectemailmarketingbe.dto.EmailDto;
import com.example.projectemailmarketingbe.dto.EmailRpDto;
import com.example.projectemailmarketingbe.dto.EmailWithProxyDto;
import com.example.projectemailmarketingbe.dto.PageResponseDto;
import com.example.projectemailmarketingbe.model.EmailEntity;
import com.example.projectemailmarketingbe.model.ProxyEntity;
import com.example.projectemailmarketingbe.model.ScheduleCronjobRunEntity;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

public interface EmailService {
    PageResponseDto<EmailRpDto> findAll(String search, int page, int size);

    List<EmailRpDto> findAll();

    EmailRpDto findByEmail(String email);

    EmailRpDto addNewEmail(EmailDto emailDto);

    void deleteEmail(String email);

    EmailRpDto updateEmail(EmailDto emailDto);

    List<EmailRpDto> addEmailsAndProxy(List<EmailDto> emailDtos);

    JavaMailSender createJavaMailSender(ScheduleCronjobRunEntity scheduleCronjobRunEntity);

    void sendMail(ScheduleCronjobRunEntity scheduleCronjobRunEntity) throws MessagingException;

    EmailEntity findEmailByIdReturnEntity(Long emailId);

    EmailEntity findEmailByEmailReturnEntity(String email);

    void sendMailTest(ScheduleCronjobRunEntity scheduleCronjobRunEntity);
}
