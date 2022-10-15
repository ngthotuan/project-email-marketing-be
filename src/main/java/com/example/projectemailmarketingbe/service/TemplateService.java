package com.example.projectemailmarketingbe.service;

import com.example.projectemailmarketingbe.dto.PageResponseDto;
import com.example.projectemailmarketingbe.dto.ProxyRpDto;
import com.example.projectemailmarketingbe.dto.ProxyUpdateDto;
import com.example.projectemailmarketingbe.dto.TemplateDto;
import com.example.projectemailmarketingbe.model.TemplateEntity;

import java.util.List;

public interface TemplateService {

    PageResponseDto<TemplateDto> findAllTemplateWithPagingAndSearch(String search, int page, int size);

    List<TemplateDto> addListTemplates(List<TemplateDto> proxyRpDtos);

    TemplateDto updateTemplate(TemplateEntity templateEntity);

    TemplateDto createTemplate(TemplateDto templateDto);

    void deleteTemplateById(Long templateId);

    TemplateDto findById(Long templateId);
}
