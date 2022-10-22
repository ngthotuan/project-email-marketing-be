package com.example.projectemailmarketingbe.mapper;

import com.example.projectemailmarketingbe.dto.TemplateDto;
import com.example.projectemailmarketingbe.dto.TemplateRpDto;
import com.example.projectemailmarketingbe.model.TemplateEntity;

public interface TemplateMapper {
    TemplateRpDto templateEntityToTemplateRpDto(TemplateEntity templateEntity);

    TemplateEntity templateDtoToTemplateEntity(TemplateDto templateDto);
}
