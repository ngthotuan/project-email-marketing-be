package com.example.projectemailmarketingbe.service;

import com.example.projectemailmarketingbe.dto.PageResponseDto;
import com.example.projectemailmarketingbe.dto.TemplateDto;
import com.example.projectemailmarketingbe.dto.TemplateRpDto;
import com.example.projectemailmarketingbe.dto.TemplateRpMDto;
import com.example.projectemailmarketingbe.model.TemplateEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TemplateService {

    PageResponseDto<TemplateRpDto> findAllTemplateWithPagingAndSearch(String search, int page, int size);

    List<TemplateRpDto> addListTemplates(List<TemplateDto> templateDtos);

    TemplateRpDto updateTemplate(TemplateEntity templateEntity);

    TemplateRpDto createTemplate(TemplateDto templateDto, List<MultipartFile> attachments);

    void deleteTemplateById(Long templateId);

    TemplateRpDto findById(Long templateId);

    TemplateEntity findByIdRPEntity(Long templateId);

    List<TemplateRpMDto> findAllTemplate();

    TemplateRpDto updateTemplate(Long templateId, MultipartFile file);

    void deleteAttachmentTemplate(Long templateId, String uniqueName);
}
