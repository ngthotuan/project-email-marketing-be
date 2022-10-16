package com.example.projectemailmarketingbe.service.Impl;

import com.example.projectemailmarketingbe.dto.EmailDto;
import com.example.projectemailmarketingbe.dto.EmailRpDto;
import com.example.projectemailmarketingbe.dto.EmailWithProxyDto;
import com.example.projectemailmarketingbe.dto.PageResponseDto;
import com.example.projectemailmarketingbe.exception.NotFoundException;
import com.example.projectemailmarketingbe.mapper.EmailMapper;
import com.example.projectemailmarketingbe.model.EmailEntity;
import com.example.projectemailmarketingbe.model.ProxyEntity;
import com.example.projectemailmarketingbe.model.ScheduleCronjobRunEntity;
import com.example.projectemailmarketingbe.repository.EmailRepository;
import com.example.projectemailmarketingbe.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static com.example.projectemailmarketingbe.constant.MessageConstant.SEND_MAIL_LOG;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final EmailRepository emailRepository;
    private final EmailMapper emailMapper;
    private final JavaMailSenderImpl javaMailSender;

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
                        .map(emailMapper::emailToEmailDto)
                        .collect(Collectors.toList()))
                .build();
        return pageResponseDto;
    }

    @Override
    public List<EmailRpDto> findAll() {
        return emailRepository.findAll().stream().map(emailMapper
                ::emailToEmailDto).collect(Collectors.toList());
    }

    @Override
    public EmailRpDto findByEmail(String email) {
        EmailEntity emailInDb = emailRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(String.format("%s not found", email)));
        return emailMapper.emailToEmailDto(emailInDb);
    }

    @Override
    public EmailRpDto addNewEmail(EmailDto email) {
        EmailEntity save = emailRepository.save(EmailEntity.builder().email(email.getEmail()).password(email.getPassword()).build());
        return emailMapper.emailToEmailDto(save);
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
        return emailMapper.emailToEmailDto(emailInDb);
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
                        .name(emailWithProxyDto.getProxyName())
                        .password(emailWithProxyDto.getPasswordProxy())
                        .port(emailWithProxyDto.getPort())
                        .username(emailWithProxyDto.getUsername())
                        .build()
                ).build()).collect(Collectors.toList());
        for (EmailEntity emailEntity : emailEntities) {
            EmailEntity save = emailRepository.save(emailEntity);
            result.add(save);
        }
        return result.stream().map(emailMapper::emailToEmailDto).collect(Collectors.toList());
    }

    @SneakyThrows
    @Override
    public JavaMailSender createJavaMailSender(ScheduleCronjobRunEntity scheduleCronjobRunEntity) {
        javaMailSender.setUsername(scheduleCronjobRunEntity.getEmailEntity().getEmail());
        javaMailSender.setPassword(scheduleCronjobRunEntity.getEmailEntity().getPassword());
        javaMailSender.setPort(587);
        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.proxy.host", scheduleCronjobRunEntity.getEmailEntity().getProxyEntity().getHost());
        props.put("mail.smtp.proxy.port", scheduleCronjobRunEntity.getEmailEntity().getProxyEntity().getPort());
        return javaMailSender;
    }

    @Override
    @Async
    public void sendMail(ScheduleCronjobRunEntity scheduleCronjobRunEntity) throws MessagingException {

        JavaMailSender mailSender = createJavaMailSender(scheduleCronjobRunEntity);
        MimeMessage message = mailSender.createMimeMessage();

        String[] emailTos = scheduleCronjobRunEntity.getEmailTo().trim().split(",");
        for (String email : emailTos) {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(scheduleCronjobRunEntity.getEmailEntity().getEmail());
            helper.setTo(email);
            helper.setSubject(scheduleCronjobRunEntity.getTemplateEntity().getSubject());
            helper.setText(scheduleCronjobRunEntity.getTemplateEntity().getContent());
            mailSender.send(message);
            log.info(SEND_MAIL_LOG, email);
        }

    }


}
