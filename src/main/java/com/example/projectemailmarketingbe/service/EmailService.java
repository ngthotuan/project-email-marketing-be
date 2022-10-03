package com.example.projectemailmarketingbe.service;

import com.example.projectemailmarketingbe.dto.EmailDto;
import com.example.projectemailmarketingbe.model.EmailEntity;

import java.util.List;

public interface EmailService {
    public List<EmailDto> findAll();
}
