package com.example.projectemailmarketingbe.mapper.impl;

import com.example.projectemailmarketingbe.dto.TemplateDto;
import com.example.projectemailmarketingbe.dto.TemplateRpDto;
import com.example.projectemailmarketingbe.dto.TemplateRpMDto;
import com.example.projectemailmarketingbe.mapper.FileMapper;
import com.example.projectemailmarketingbe.mapper.TemplateMapper;
import com.example.projectemailmarketingbe.model.TemplateEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TemplateMapperImpl implements TemplateMapper {
    private final FileMapper fileMapper;

    @Override
    public TemplateRpDto templateEntityToTemplateRpDto(TemplateEntity templateEntity) {
        return TemplateRpDto.builder()
                .id(templateEntity.getId())
                .name(templateEntity.getName())
                .content(templateEntity.getContent())
                .subject(templateEntity.getSubject())
                .files(templateEntity.getFileEntities() != null
                        ? templateEntity.getFileEntities().stream().map(fileMapper::fileEntityToFileRpDto).collect(Collectors.toList())
                        : null)
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
