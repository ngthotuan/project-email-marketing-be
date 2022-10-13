package com.example.projectemailmarketingbe.controller;

import com.example.projectemailmarketingbe.dto.ResponseBodyDto;
import com.example.projectemailmarketingbe.dto.TemplateDto;
import com.example.projectemailmarketingbe.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/templates")
@RequiredArgsConstructor
public class TemplateController {
    private final TemplateService templateService;
    @GetMapping("/")
    public ResponseEntity<ResponseBodyDto<List<TemplateDto>>> findAllTemplateByName(@RequestParam("name") String name){
        ResponseBodyDto<List<TemplateDto>> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(templateService.findAllByName(name));
        responseBodyDto.setStatusCode(200);
        return ResponseEntity.ok(responseBodyDto);
    }

    @GetMapping("/{templateId}")
    public ResponseEntity<ResponseBodyDto<TemplateDto>> findTemplateById(@PathVariable("templateId") Long templateId) {
        ResponseBodyDto<TemplateDto> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(templateService.findById(templateId));
        responseBodyDto.setStatusCode(200);
        return ResponseEntity.ok(responseBodyDto);
    }

    @DeleteMapping("/{templateId}")
    public ResponseEntity<ResponseBodyDto<TemplateDto>> deleteTemplateById(@PathVariable("templateId") Long templateId) {
        ResponseBodyDto<TemplateDto> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setStatusCode(200);
        return ResponseEntity.ok(responseBodyDto);
    }

}
