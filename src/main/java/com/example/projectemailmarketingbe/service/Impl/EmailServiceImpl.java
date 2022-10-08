package com.example.projectemailmarketingbe.service.Impl;

import com.example.projectemailmarketingbe.dto.EmailDto;
import com.example.projectemailmarketingbe.exception.NotFoundException;
import com.example.projectemailmarketingbe.model.EmailEntity;
import com.example.projectemailmarketingbe.repository.EmailRepository;
import com.example.projectemailmarketingbe.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final EmailRepository emailRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<EmailDto> findAll() {
        return emailRepository.findAll().stream()
                .map(emailEntity -> EmailDto.builder().email(emailEntity.getEmail()).build())
                .collect(Collectors.toList());
    }

    @Override
    public EmailDto findByEmail(String email) {
        EmailEntity byId = emailRepository.findByEmail(email).orElseThrow(()->new NotFoundException(String.format("{} not found",email)));
        return modelMapper.map(byId, EmailDto.class);
    }

    @Override
    public EmailDto addNewEmail(String email) {
        EmailEntity save = emailRepository.save(EmailEntity.builder().email(email).build());
        return modelMapper.map(save, EmailDto.class);
    }

    @Override
    public void deleteEmail(String email) {
        emailRepository.delete(EmailEntity.builder().email(email).build());
    }

//    @Override
//    public EmailDto updateEmail(String email) {
//        emailRepository.findById(email).ifPresentOrElse((emailEntity -> {
//            emailEntity.setEmail(email);
//                    EmailEntity save = emailRepository.save(emailEntity);
//                    modelMapper.map(save, EmailDto.class);
//        }),
//                () -> throw new NotFoundException());
//        return ;
//    }


}
