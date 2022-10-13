package com.example.projectemailmarketingbe.service.Impl;

import com.example.projectemailmarketingbe.dto.TemplateDto;
import com.example.projectemailmarketingbe.exception.NotFoundException;
import com.example.projectemailmarketingbe.model.TemplateEntity;
import com.example.projectemailmarketingbe.repository.TemplateRepository;
import com.example.projectemailmarketingbe.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {
    private final TemplateRepository templateRepository;
    private final ModelMapper modelMapper;
    @Override
    public TemplateDto findById(Long templateId) {
        TemplateEntity template = templateRepository.findById(templateId).orElseThrow(() -> new NotFoundException("Template id not found"));
        return modelMapper.map(template, TemplateDto.class);
    }

    @Override
    public List<TemplateDto> findAllByName(String name) {
        return templateRepository.findAllByName(name);
    }

    @Override
    public void deleteTemplate(Long templateId) {
        TemplateEntity template = templateRepository.findById(templateId).orElseThrow(() -> new NotFoundException("Template id not found"));
        templateRepository.delete(template);
    }
}
