package com.example.projectemailmarketingbe.controller;

import com.example.projectemailmarketingbe.dto.*;
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
        return ResponseEntity.ok().build();
    }

    @PutMapping("/email")
    public ResponseEntity<?> updateEmail(@RequestBody EmailDto emailDto) {
        ResponseBodyDto<EmailRpDto> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(emailService.updateEmail(emailDto));
        responseBodyDto.setStatusCode(200);
        return ResponseEntity.ok(responseBodyDto);
    }

    @PostMapping("/email")
    public ResponseEntity<?> createEmail(@RequestBody EmailDto emailDto){
        ResponseBodyDto<EmailRpDto> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(emailService.addNewEmail(emailDto));
        responseBodyDto.setStatusCode(201);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBodyDto);
    }

    @PostMapping("/")
    public ResponseEntity<?> createEmailWithProxies(@RequestBody List<EmailWithProxyDto> emailWithProxyDtos){
        ResponseBodyDto<List<EmailRpDto>> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(emailService.addEmailsAndProxy(emailWithProxyDtos));
        responseBodyDto.setStatusCode(201);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBodyDto);
    }

}
