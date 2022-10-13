package com.example.projectemailmarketingbe.service;

import com.example.projectemailmarketingbe.dto.TemplateDto;

import java.util.List;

public interface TemplateService {
    TemplateDto findById(Long templateId);

    List<TemplateDto> findAllByName(String name);

    void deleteTemplate(Long templateId);
}
