package com.example.projectemailmarketingbe.service.Impl;

import com.example.projectemailmarketingbe.dto.PageResponseDto;
import com.example.projectemailmarketingbe.dto.TemplateDto;
import com.example.projectemailmarketingbe.exception.NotFoundException;
import com.example.projectemailmarketingbe.mapper.TemplateMapper;
import com.example.projectemailmarketingbe.model.TemplateEntity;
import com.example.projectemailmarketingbe.repository.TemplateRepository;
import com.example.projectemailmarketingbe.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.projectemailmarketingbe.constant.MessageConstant.TEMPLATE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {
    private final TemplateRepository templateRepository;
    private final TemplateMapper templateMapper;

    @Override
    public PageResponseDto<TemplateDto> findAllTemplateWithPagingAndSearch(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TemplateEntity> pageEmail = search.isBlank()
                ? templateRepository.findAll(pageable)
                : templateRepository.findAll(search, pageable);
        PageResponseDto pageResponseDto = PageResponseDto
                .builder()
                .page(page)
                .size(size)
                .totalPages(pageEmail.getTotalPages())
                .totalElements(pageEmail.getTotalElements())
                .elements(pageEmail.stream()
                        .map(templateMapper::templateEntityToTemplateDto)
                        .collect(Collectors.toList()))
                .build();
        return pageResponseDto;
    }

    @Override
    public List<TemplateDto> addListTemplates(List<TemplateDto> proxyRpDtos) {
        return null;
    }

    @Override
    public TemplateDto updateTemplate(TemplateEntity templateEntity) {
        TemplateEntity entity = findByIdRPEntity(templateEntity.getTemplateId());
        return templateMapper.templateEntityToTemplateDto(templateRepository.save(templateEntity));
    }

    @Override
    public TemplateDto createTemplate(TemplateDto templateDto) {
        TemplateEntity saved = templateRepository.save(templateMapper.templateDtoToTemplateEntity(templateDto));
        return templateMapper.templateEntityToTemplateDto(saved);
    }

    @Override
    public void deleteTemplateById(Long templateId) {
        TemplateEntity byId = findByIdRPEntity(templateId);
        templateRepository.delete(byId);
    }

    @Override
    public TemplateDto findById(Long templateId) {
        TemplateEntity templateEntity = findByIdRPEntity(templateId);
        return templateMapper.templateEntityToTemplateDto(templateEntity);
    }

    private TemplateEntity findByIdRPEntity(Long proxyId) {
        return templateRepository.findById(proxyId).orElseThrow(
                () -> new NotFoundException(TEMPLATE_NOT_FOUND));
    }
}
