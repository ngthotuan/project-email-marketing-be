package com.example.projectemailmarketingbe.mapper;

import com.example.projectemailmarketingbe.dto.TemplateDto;
import com.example.projectemailmarketingbe.model.TemplateEntity;

public interface TemplateMapper {
    TemplateDto templateEntityToTemplateDto(TemplateEntity templateEntity);
    TemplateEntity templateDtoToTemplateEntity(TemplateDto templateDto);
}
