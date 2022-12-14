package com.example.projectemailmarketingbe.service.Impl;

import com.example.projectemailmarketingbe.dto.EmailDto;
import com.example.projectemailmarketingbe.dto.EmailRpDto;
import com.example.projectemailmarketingbe.dto.PageResponseDto;
import com.example.projectemailmarketingbe.exception.NotFoundException;
import com.example.projectemailmarketingbe.mapper.EmailMapper;
import com.example.projectemailmarketingbe.model.EmailEntity;
import com.example.projectemailmarketingbe.model.FileEntity;
import com.example.projectemailmarketingbe.model.ProxyEntity;
import com.example.projectemailmarketingbe.model.ScheduleCronjobRunEntity;
import com.example.projectemailmarketingbe.repository.EmailRepository;
import com.example.projectemailmarketingbe.repository.FileRepository;
import com.example.projectemailmarketingbe.service.EmailService;
import com.example.projectemailmarketingbe.service.FilesStorageService;
import com.example.projectemailmarketingbe.service.ProxyService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static com.example.projectemailmarketingbe.constant.MessageConstant.SEND_MAIL_LOG;
import static com.example.projectemailmarketingbe.constant.MessageConstant.USER_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final EmailRepository emailRepository;
    private final EmailMapper emailMapper;
    private final JavaMailSenderImpl javaMailSender;
    private final ProxyService proxyService;
    private final FilesStorageService filesStorageService;
    private final FileRepository fileRepository;


    @Override
    public PageResponseDto<EmailRpDto> findAll(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<EmailEntity> pageEmail = search.isBlank()
                ? emailRepository.findAll(pageable)
                : emailRepository.findAllByEmailAndEmailName(search, pageable);
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
        return emailRepository.findAll(Sort.by("createdDate").descending()).stream().map(emailMapper
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
        ProxyEntity proxyByIdReturnEntity = proxyService.findProxyByIdReturnEntity(email.getProxyId());
        EmailEntity save = emailRepository.save(EmailEntity.builder()
                .email(email.getEmail())
                .password(email.getPassword())
                .emailName(email.getEmailName())
                .proxyEntity(proxyByIdReturnEntity).build());
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
        emailInDb.setEmailName(email.getEmailName());
        ProxyEntity proxyByIdReturnEntity = proxyService.findProxyByIdReturnEntity(email.getProxyId());
        emailInDb.setProxyEntity(proxyByIdReturnEntity);
        return emailMapper.emailToEmailDto(emailInDb);
    }

    @Override
    @Transactional
    public List<EmailRpDto> addEmailsAndProxy(List<EmailDto> emailDtos) {
        List<EmailEntity> result = new ArrayList<>();
        List<EmailEntity> emailEntities = emailDtos.stream().map(emailDto -> EmailEntity.builder()
                .email(emailDto.getEmail())
                .password(emailDto.getPassword())
                .emailName(emailDto.getEmailName())
                .proxyEntity(proxyService.findProxyByIdReturnEntity(emailDto.getProxyId())
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
        javaMailSender.setHost("smtp.gmail.com");
        Properties props = javaMailSender.getJavaMailProperties();

        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");
        if (scheduleCronjobRunEntity.getEmailEntity().getProxyEntity() != null) {
            props.put("proxySet", "true");
            props.put("mail.smtp.proxy.user", scheduleCronjobRunEntity.getEmailEntity().getProxyEntity().getUsername());
            props.put("mail.smtp.proxy.password", scheduleCronjobRunEntity.getEmailEntity().getProxyEntity().getPassword());
            props.put("mail.smtp.proxy.host", scheduleCronjobRunEntity.getEmailEntity().getProxyEntity().getHost());
            props.put("mail.smtp.proxy.port", scheduleCronjobRunEntity.getEmailEntity().getProxyEntity().getPort());
        }
        return javaMailSender;
    }

    @SneakyThrows
    @Override
    public void sendMail(ScheduleCronjobRunEntity scheduleCronjobRunEntity) throws MessagingException {

        JavaMailSender mailSender = createJavaMailSender(scheduleCronjobRunEntity);
        MimeMessage message = mailSender.createMimeMessage();

        String[] emailTos = scheduleCronjobRunEntity.getEmailTo().trim().split(",");
        for (String email : emailTos) {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(new InternetAddress(scheduleCronjobRunEntity.getEmailEntity().getEmail(),
                    scheduleCronjobRunEntity.getEmailEntity().getEmailName()));
            helper.setTo(email);
            helper.setSubject(scheduleCronjobRunEntity.getTemplateEntity().getSubject());
            List<FileEntity> fileEntities = fileRepository.findByTemplateEntity(scheduleCronjobRunEntity.getTemplateEntity());
            fileEntities.forEach(fileEntity -> {
                try {
                    helper.addAttachment(fileEntity.getOriginName(), filesStorageService.load(fileEntity.getName()));
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });

            helper.setText(scheduleCronjobRunEntity.getTemplateEntity().getContent(), true);
            mailSender.send(message);
            log.info(SEND_MAIL_LOG, email);
        }
    }

    @Override
    public EmailEntity findEmailByIdReturnEntity(Long emailId) {
        return emailRepository.findById(emailId).orElseThrow(
                () -> new NotFoundException(USER_NOT_FOUND));
    }

    @Override
    public EmailEntity findEmailByEmailReturnEntity(String email) {
        return emailRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException(USER_NOT_FOUND));
    }

    @SneakyThrows
    @Override
    public void sendMailTest(ScheduleCronjobRunEntity scheduleCronjobRunEntity) {
        Properties props = new Properties();
        props.setProperty("mail.smtp.proxy.host",
                scheduleCronjobRunEntity.getEmailEntity().getProxyEntity().getHost());
        props.setProperty("mail.smtp.proxy.port",
                scheduleCronjobRunEntity.getEmailEntity().getProxyEntity().getPort());
        props.setProperty("mail.smtp.proxy.user",
                scheduleCronjobRunEntity.getEmailEntity().getProxyEntity().getUsername());
        props.setProperty("mail.smtp.proxy.password",
                scheduleCronjobRunEntity.getEmailEntity().getProxyEntity().getPassword());
        props.setProperty("mail.smtp.user", scheduleCronjobRunEntity.getEmailEntity().getEmail());
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.debug", "true");
        props.setProperty("mail.smtp.auth", "true");

        var session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(scheduleCronjobRunEntity.getEmailEntity().getEmail(),
                        scheduleCronjobRunEntity.getEmailEntity().getPassword());
            }
        });
        String[] emailTos = scheduleCronjobRunEntity.getEmailTo().trim().split(",");
        InternetAddress[] dests = new InternetAddress[emailTos.length];
        for (int i = 0; i < emailTos.length; i++) {
            dests[i] = new InternetAddress(emailTos[i].trim().toLowerCase());
        }
        MimeMessage message = new MimeMessage(session);
        message.setFrom(scheduleCronjobRunEntity.getEmailEntity().getEmail());
        message.setRecipients(Message.RecipientType.TO, dests);
        message.setSubject(scheduleCronjobRunEntity.getTemplateEntity().getSubject(),"UTF-8");
        message.setContent(scheduleCronjobRunEntity.getTemplateEntity().getContent(), "text/html;charset=utf-8");
        Transport.send(message);
        log.info(SEND_MAIL_LOG, emailTos);
    }
}
