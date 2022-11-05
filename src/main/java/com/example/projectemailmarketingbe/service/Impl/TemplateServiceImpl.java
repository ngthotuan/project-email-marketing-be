package com.example.projectemailmarketingbe.service.Impl;

import com.example.projectemailmarketingbe.dto.PageResponseDto;
import com.example.projectemailmarketingbe.dto.TemplateDto;
import com.example.projectemailmarketingbe.dto.TemplateRpDto;
import com.example.projectemailmarketingbe.dto.TemplateRpMDto;
import com.example.projectemailmarketingbe.exception.NotFoundException;
import com.example.projectemailmarketingbe.mapper.TemplateMapper;
import com.example.projectemailmarketingbe.model.FileEntity;
import com.example.projectemailmarketingbe.model.TemplateEntity;
import com.example.projectemailmarketingbe.repository.FileRepository;
import com.example.projectemailmarketingbe.repository.TemplateRepository;
import com.example.projectemailmarketingbe.service.FilesStorageService;
import com.example.projectemailmarketingbe.service.TemplateService;
import com.example.projectemailmarketingbe.utils.FileNameUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.projectemailmarketingbe.constant.MessageConstant.TEMPLATE_NOT_FOUND;
import static com.example.projectemailmarketingbe.constant.PathConstant.filePath;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {
    private final TemplateRepository templateRepository;
    private final TemplateMapper templateMapper;
    private final FilesStorageService filesStorageService;
    private final FileNameUtils fileNameUtils;
    private final FileRepository fileRepository;
    private final URI fileEndpoint;

    @Override
    public PageResponseDto<TemplateRpDto> findAllTemplateWithPagingAndSearch(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
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
                        .map(templateMapper::templateEntityToTemplateRpDto)
                        .collect(Collectors.toList()))
                .build();
        return pageResponseDto;
    }

    @Override
    public List<TemplateRpDto> addListTemplates(List<TemplateDto> templateDtos) {
        return null;
    }

    @Override
    @Transactional
    public TemplateRpDto updateTemplate(TemplateEntity templateEntity) {
        TemplateEntity entity = findByIdRPEntity(templateEntity.getId());
        return templateMapper.templateEntityToTemplateRpDto(templateRepository.save(templateEntity));
    }

    @Override
    public TemplateRpDto createTemplate(TemplateDto templateDto, List<MultipartFile> files) {
        TemplateEntity templateEntity = templateMapper.templateDtoToTemplateEntity(templateDto);
        List<FileEntity> fileEntities = files.stream().map(file -> {
            String fileNameUnique = fileNameUtils.createFileNameUnique(Objects.requireNonNull(file.getOriginalFilename()));
            String url = String.format("%s", fileEndpoint.toString() + String.format(filePath, fileNameUnique));
            filesStorageService.save(file, fileNameUnique);
            var fileEntity = FileEntity.builder().name(fileNameUnique).originName(file.getOriginalFilename()).templateEntity(templateEntity).fileUrl(url).build();

            return fileEntity;
        }).collect(Collectors.toList());
        templateEntity.setFileEntities(fileEntities);
        TemplateEntity saved = templateRepository.save(templateEntity);
        return templateMapper.templateEntityToTemplateRpDto(saved);
    }

    @Override
    public void deleteTemplateById(Long templateId) {
        TemplateEntity byId = findByIdRPEntity(templateId);
        templateRepository.delete(byId);
    }

    @Override
    public TemplateRpDto findById(Long templateId) {
        TemplateEntity templateEntity = findByIdRPEntity(templateId);
        return templateMapper.templateEntityToTemplateRpDto(templateEntity);
    }

    @Override
    public TemplateEntity findByIdRPEntity(Long templateId) {
        return templateRepository.findById(templateId).orElseThrow(
                () -> new NotFoundException(TEMPLATE_NOT_FOUND));
    }

    @Override
    public List<TemplateRpMDto> findAllTemplate() {
        return templateRepository.findAll().stream()
                .map(templateMapper::templateEntityToTemplateRpMDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TemplateRpDto updateTemplate(Long templateId, MultipartFile file) {
        TemplateEntity templateEntity = findByIdRPEntity(templateId);
        String fileNameUnique = fileNameUtils.createFileNameUnique(Objects.requireNonNull(file.getOriginalFilename()));
        filesStorageService.save(file, fileNameUnique);
        String url = String.format("%s", fileEndpoint.toString() + String.format(filePath, fileNameUnique));
        var fileEntity = FileEntity.builder()
                .name(fileNameUnique)
                .originName(file.getOriginalFilename())
                .templateEntity(templateEntity)
                .fileUrl(url)
                .build();
        List<FileEntity> fileEntities = templateEntity.getFileEntities();
        fileEntities.add(fileEntity);
        templateEntity.setFileEntities(fileEntities);
        return templateMapper.templateEntityToTemplateRpDto(templateEntity);
    }

    @Override
    public void deleteAttachmentTemplate(Long templateId, String uniqueName) {
//        Integer count = fileRepository.findByNameAndTemplateId(templateId, uniqueName);
//        if (count < 1) {
//            throw new NotFoundException("template does not have attachment this");
//        }
        fileRepository.delete(fileRepository.findByName(uniqueName).get());
        filesStorageService.deleteFile(uniqueName);
    }


}
