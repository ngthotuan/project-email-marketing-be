package com.example.projectemailmarketingbe.controller;

import com.example.projectemailmarketingbe.dto.EmailDto;
import com.example.projectemailmarketingbe.dto.EmailRpDto;
import com.example.projectemailmarketingbe.dto.PageResponseDto;
import com.example.projectemailmarketingbe.dto.ResponseBodyDto;
import com.example.projectemailmarketingbe.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/emails")
public class EmailController {
    private final EmailService emailService;

    @GetMapping("")
    public ResponseEntity<ResponseBodyDto<PageResponseDto<EmailRpDto>>> findAll(@RequestParam(name = "page", required = false,
            defaultValue = "1") int page,
                                                                                @RequestParam(name = "size", required = false,
                                                                                        defaultValue = "20") int size,
                                                                                @RequestParam(name = "search"
                                                                                        , defaultValue = "",
                                                                                        required = false) String search) {
        ResponseBodyDto<PageResponseDto<EmailRpDto>> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(emailService.findAll(search, page - 1, size));
        responseBodyDto.setStatusCode(200);
        return ResponseEntity.ok(responseBodyDto);
    }

    @GetMapping("/")
    public ResponseEntity<ResponseBodyDto<List<EmailRpDto>>> findAll() {
        ResponseBodyDto<List<EmailRpDto>> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(emailService.findAll());
        responseBodyDto.setStatusCode(200);
        return ResponseEntity.ok(responseBodyDto);
    }

    @GetMapping("/{email}")
    public ResponseEntity<ResponseBodyDto<EmailRpDto>> findTemplateById(@PathVariable("email") String email) {
        ResponseBodyDto<EmailRpDto> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(emailService.findByEmail(email));
        responseBodyDto.setStatusCode(200);
        return ResponseEntity.ok(responseBodyDto);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteEmail(@PathVariable("email") String email) {
        emailService.deleteEmail(email);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/email")
    public ResponseEntity<?> updateEmail(@RequestBody EmailDto emailDto) {
        ResponseBodyDto<EmailRpDto> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(emailService.updateEmail(emailDto));
        responseBodyDto.setStatusCode(200);
        return ResponseEntity.ok(responseBodyDto);
    }

    @PostMapping("/email")
    public ResponseEntity<?> createEmail(@RequestBody EmailDto emailDto) {
        ResponseBodyDto<EmailRpDto> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(emailService.addNewEmail(emailDto));
        responseBodyDto.setStatusCode(201);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBodyDto);
    }

    @PostMapping("/")
    public ResponseEntity<?> createEmailWithProxies(@RequestBody List<EmailDto> emailDtos) {
        ResponseBodyDto<List<EmailRpDto>> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(emailService.addEmailsAndProxy(emailDtos));
        responseBodyDto.setStatusCode(201);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBodyDto);
    }

}
