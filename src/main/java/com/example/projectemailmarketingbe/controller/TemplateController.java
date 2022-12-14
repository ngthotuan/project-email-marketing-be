package com.example.projectemailmarketingbe.controller;

import com.example.projectemailmarketingbe.dto.*;
import com.example.projectemailmarketingbe.model.TemplateEntity;
import com.example.projectemailmarketingbe.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/templates")
@RequiredArgsConstructor
public class TemplateController {
    private final TemplateService templateService;

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        ResponseBodyDto<List<TemplateRpMDto>> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(templateService.findAllTemplate());
        responseBodyDto.setStatusCode(200);
        return ResponseEntity.ok(responseBodyDto);
    }

    @GetMapping("")
    public ResponseEntity<ResponseBodyDto<PageResponseDto<TemplateRpDto>>> findAll(@RequestParam(name = "page", required = false,
            defaultValue = "1") int page,
                                                                                   @RequestParam(name = "size", required = false,
                                                                                           defaultValue = "20") int size,
                                                                                   @RequestParam(name = "search"
                                                                                           , defaultValue = "",
                                                                                           required = false) String search) {
        ResponseBodyDto<PageResponseDto<TemplateRpDto>> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(templateService.findAllTemplateWithPagingAndSearch(search, page - 1, size));
        responseBodyDto.setStatusCode(200);
        return ResponseEntity.ok(responseBodyDto);
    }

    @GetMapping("/{templateId}")
    public ResponseEntity<ResponseBodyDto<TemplateRpDto>> findTemplateById(@PathVariable("templateId") Long templateId) {
        ResponseBodyDto<TemplateRpDto> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(templateService.findById(templateId));
        responseBodyDto.setStatusCode(200);
        return ResponseEntity.ok(responseBodyDto);
    }

    @DeleteMapping("/{templateId}")
    public ResponseEntity<?> deleteTemplate(@PathVariable("templateId") Long templateId) {
        templateService.deleteTemplateById(templateId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/template")
    public ResponseEntity<?> updateTemplate(@RequestBody TemplateEntity templateEntity) {
        ResponseBodyDto<TemplateRpDto> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(templateService.updateTemplate(templateEntity));
        responseBodyDto.setStatusCode(200);
        return ResponseEntity.ok(responseBodyDto);
    }

    @PostMapping(value = "/template")
    public ResponseEntity<?> createTemplate(@RequestPart("data") TemplateDto templateDto,
                                            @RequestPart("files") List<MultipartFile> attachments) {
        ResponseBodyDto<TemplateRpDto> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(templateService.createTemplate(templateDto, attachments));
        responseBodyDto.setStatusCode(201);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBodyDto);
    }

    @DeleteMapping("/{templateId}/file/{fileName}")
    public ResponseEntity<?> deleteFileInTemplate(@PathVariable(name = "templateId") Long templateId,
                                                  @PathVariable(name = "fileName") String fileName) {
        templateService.deleteAttachmentTemplate(templateId, fileName);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{templateId}/file")
    public ResponseEntity<?> updateFileInTemplate(@PathVariable(name = "templateId") Long templateId,
                                                  @RequestPart(name = "file") MultipartFile file) {
        ResponseBodyDto<TemplateRpDto> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(templateService.updateTemplate(templateId, file));
        responseBodyDto.setStatusCode(200);
        return ResponseEntity.ok(responseBodyDto);
    }

}
