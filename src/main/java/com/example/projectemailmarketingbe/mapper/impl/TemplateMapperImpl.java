package com.example.projectemailmarketingbe.mapper.impl;

import com.example.projectemailmarketingbe.dto.TemplateDto;
import com.example.projectemailmarketingbe.dto.TemplateRpDto;
import com.example.projectemailmarketingbe.dto.TemplateRpMDto;
import com.example.projectemailmarketingbe.mapper.TemplateMapper;
import com.example.projectemailmarketingbe.model.TemplateEntity;
import org.springframework.stereotype.Component;

@Component
public class TemplateMapperImpl implements TemplateMapper {
    @Override
    public TemplateRpDto templateEntityToTemplateRpDto(TemplateEntity templateEntity) {
        return TemplateRpDto.builder()
                .id(templateEntity.getId())
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

    @Override
    public TemplateRpMDto templateEntityToTemplateRpMDto(TemplateEntity templateEntity) {
        return TemplateRpMDto.builder()
                .id(templateEntity.getId())
                .name(templateEntity.getName())
                .subject(templateEntity.getSubject())
                .build();
    }
}
