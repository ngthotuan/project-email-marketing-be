package com.example.projectemailmarketingbe.service;

import com.example.projectemailmarketingbe.dto.TemplateDto;

import java.util.List;
import java.util.UUID;

public interface TemplateService {
    TemplateDto findById(UUID templateId);
    List<TemplateDto> findAllByName(String name);
    void deleteTemplate(UUID templateId);
}
