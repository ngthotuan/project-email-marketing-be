package com.example.projectemailmarketingbe.controller;

import com.example.projectemailmarketingbe.dto.FileInfo;
import com.example.projectemailmarketingbe.dto.FileRpDto;
import com.example.projectemailmarketingbe.dto.ResponseBodyDto;
import com.example.projectemailmarketingbe.exception.NotFoundException;
import com.example.projectemailmarketingbe.model.FileEntity;
import com.example.projectemailmarketingbe.repository.FileRepository;
import com.example.projectemailmarketingbe.service.FilesStorageService;
import com.example.projectemailmarketingbe.utils.FileNameUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {
    private final FilesStorageService filesStorageService;
    private final FileNameUtils fileNameUtils;
    private final FileRepository fileRepository;

    @PostMapping("/file")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        String message = "";
        try {
            var fileNameUnique = fileNameUtils.createFileNameUnique(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            String url = filesStorageService.save(multipartFile, fileNameUnique);
            message = "Uploaded the file successfully: " + multipartFile.getOriginalFilename();
            FileRpDto build = FileRpDto.builder().message(message).url(url).build();
            return ResponseEntity.status(HttpStatus.OK).body(
                    ResponseBodyDto.builder().data(build).statusCode(HttpStatus.OK.value()).build());
        } catch (Exception e) {
            message = "Could not upload the file: " + multipartFile.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                    ResponseBodyDto.builder().data(FileRpDto.builder().message(message).build()).statusCode(HttpStatus.EXPECTATION_FAILED.value()).build());
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
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        Resource file = filesStorageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE).body(file);
    }

    @GetMapping(value = "/attachments/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = filesStorageService.load(filename);
        var fileEntity = fileRepository.findByName(filename).orElseThrow(() -> new NotFoundException("Cannot " +
                "fine file has file name" + filename));
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + fileEntity.getOriginName() + "\"").body(file);
    }
}
