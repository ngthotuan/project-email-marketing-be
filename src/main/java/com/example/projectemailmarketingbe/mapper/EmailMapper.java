package com.example.projectemailmarketingbe.mapper;

import com.example.projectemailmarketingbe.dto.EmailRpDto;
import com.example.projectemailmarketingbe.model.EmailEntity;

public interface EmailMapper {
    EmailRpDto emailToEmailDto(EmailEntity emailEntity);
}
