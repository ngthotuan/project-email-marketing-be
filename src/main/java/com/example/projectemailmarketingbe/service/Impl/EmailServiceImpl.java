package com.example.projectemailmarketingbe.service.Impl;

import com.example.projectemailmarketingbe.dto.EmailDto;
import com.example.projectemailmarketingbe.dto.EmailRpDto;
import com.example.projectemailmarketingbe.dto.PageResponseDto;
import com.example.projectemailmarketingbe.exception.NotFoundException;
import com.example.projectemailmarketingbe.model.EmailEntity;
import com.example.projectemailmarketingbe.repository.EmailRepository;
import com.example.projectemailmarketingbe.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final EmailRepository emailRepository;
    private final ModelMapper modelMapper;

    @Override
    public PageResponseDto<EmailRpDto> findAll(String search, int page, int size) {
        Pageable pageable = (Pageable) PageRequest.of(page, size);
        Page<EmailEntity> pageEmail = search.isBlank()
            ? emailRepository.findAll(pageable)
                :emailRepository.findAllByEmailLike(search, pageable);
        PageResponseDto pageResponseDto = PageResponseDto
                .builder()
                .page(page)
                .size(size)
                .totalPages(pageEmail.getTotalPages())
                .totalElements(pageEmail.getTotalElements())
                .elements(pageEmail.stream().map(emailEntity -> EmailRpDto.builder().email(emailEntity.getEmail()).build())
                        .collect(Collectors.toList()))
                .build();
        return pageResponseDto;
    }

    @Override
    public EmailRpDto findByEmail(String email) {
        EmailEntity byId = emailRepository.findByEmail(email).orElseThrow(()->new NotFoundException(String.format("{} not found",email)));
        return modelMapper.map(byId, EmailRpDto.class);
    }

    @Override
    public EmailRpDto addNewEmail(EmailDto email) {
        EmailEntity save = emailRepository.save(EmailEntity.builder().email(email.getEmail()).password(email.getPassword()).build());
        return modelMapper.map(save, EmailRpDto.class);
    }

    @Override
    public void deleteEmail(String email) {
        EmailEntity byId = emailRepository.findByEmail(email).orElseThrow(()->new NotFoundException(String.format("{}" +
                " not" +
                " found",email)));
        emailRepository.delete(byId);
    }

    @Override
    @Transactional
    public EmailRpDto updateEmail(EmailDto email) {
        EmailEntity byId = emailRepository.findByEmail(email.getEmail()).orElseThrow(()->new NotFoundException(String.format(
                "{} " +
                "not" +
                " found",email)));
        byId.setEmail(email.getEmail());
        byId.setPassword(email.getPassword());
        return modelMapper.map(byId, EmailRpDto.class);
    }


}
