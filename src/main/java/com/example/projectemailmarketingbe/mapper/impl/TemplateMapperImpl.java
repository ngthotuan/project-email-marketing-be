package com.example.projectemailmarketingbe.mapper.impl;

import com.example.projectemailmarketingbe.dto.TemplateDto;
import com.example.projectemailmarketingbe.mapper.TemplateMapper;
import com.example.projectemailmarketingbe.model.TemplateEntity;
import org.springframework.stereotype.Component;

@Component
public class TemplateMapperImpl implements TemplateMapper {
    @Override
    public TemplateDto templateEntityToTemplateDto(TemplateEntity templateEntity) {
        return TemplateDto.builder()
                .name(templateEntity.getName())
                .content(templateEntity.getContent())
                .subject(templateEntity.getSubject())
                .build();
    }

    @Override
    public TemplateEntity templateDtoToTemplateEntity(TemplateDto templateDto) {
        return TemplateEntity.builder()
                .name(templateDto.getName())
                .subject(templateDto.getSubject())
                .content(templateDto.getContent())
                .build();
    }
}
