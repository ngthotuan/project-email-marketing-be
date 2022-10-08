package com.example.projectemailmarketingbe.controller;

import com.example.projectemailmarketingbe.dto.EmailDto;
import com.example.projectemailmarketingbe.dto.ResponseBodyDto;
import com.example.projectemailmarketingbe.dto.TemplateDto;
import com.example.projectemailmarketingbe.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/emails")
public class EmailController {
    private final EmailService emailService;
    @GetMapping("/")
    public ResponseEntity<ResponseBodyDto<List<EmailDto>>> findAll(){
        ResponseBodyDto<List<EmailDto>> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(emailService.findAll());
        responseBodyDto.setStatusCode(200);
        return ResponseEntity.ok(responseBodyDto);
    }

    @GetMapping("/{email}")
    public ResponseEntity<ResponseBodyDto<EmailDto>> findTemplateById(@PathVariable("email") String email){
        ResponseBodyDto<EmailDto> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(emailService.findByEmail(email));
        responseBodyDto.setStatusCode(200);
        return ResponseEntity.ok(responseBodyDto);
    }

}
