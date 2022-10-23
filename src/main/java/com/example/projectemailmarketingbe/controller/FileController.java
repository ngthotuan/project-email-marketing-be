package com.example.projectemailmarketingbe.controller;

import com.example.projectemailmarketingbe.service.FilesStorageService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {
    private final FilesStorageService filesStorageService;
    @PostMapping("/file")
    public String uploadFile(@RequestParam("file")MultipartFile multipartFile){
        return filesStorageService.save(multipartFile);
    }
}
