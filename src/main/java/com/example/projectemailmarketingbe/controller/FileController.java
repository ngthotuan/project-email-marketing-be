package com.example.projectemailmarketingbe.controller;

import com.example.projectemailmarketingbe.dto.FileInfo;
import com.example.projectemailmarketingbe.dto.FileRpDtp;
import com.example.projectemailmarketingbe.dto.ResponseBodyDto;
import com.example.projectemailmarketingbe.service.FilesStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.projectemailmarketingbe.constant.PathConstant.filePath;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {
    private final FilesStorageService filesStorageService;
    private final URI fileEndpoint;

    @PostMapping("/file")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        String message = "";
        try {
            String fileName = filesStorageService.save(multipartFile);
            message = "Uploaded the file successfully: " + multipartFile.getOriginalFilename();
            String url = String.format("%s", fileEndpoint.toString() + String.format(filePath, fileName));
            FileRpDtp build = FileRpDtp.builder().message(message).url(url).build();
            return ResponseEntity.status(HttpStatus.OK).body(
                    ResponseBodyDto.builder().data(build).statusCode(HttpStatus.OK.value()).build());
        } catch (Exception e) {
            message = "Could not upload the file: " + multipartFile.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                    ResponseBodyDto.builder().data(FileRpDtp.builder().message(message).build()).statusCode(HttpStatus.EXPECTATION_FAILED.value()).build());
        }
    }

    @GetMapping("")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = filesStorageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FileController.class, "getFile", path.getFileName().toString()).build().toString();

            return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping(value = "/{filename:.+}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = filesStorageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE).body(file);
    }
}
