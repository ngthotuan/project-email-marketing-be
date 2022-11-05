package com.example.projectemailmarketingbe.service.Impl;

import com.example.projectemailmarketingbe.service.FilesStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

import static com.example.projectemailmarketingbe.constant.PathConstant.imagePath;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilesStorageServiceImpl implements FilesStorageService {
    private final Path root = Paths.get("uploads");
    private final URI fileEndpoint;


    @Override
    public void init() {
        try {
            if (!Files.exists(root)) {
                Files.createDirectory(root);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public String save(MultipartFile file, String fileNameUnique) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(Objects.requireNonNull(fileNameUnique)));
            String url = String.format("%s", fileEndpoint.toString() + String.format(imagePath, fileNameUnique));
            return url;
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }


    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

    @Override
    public void deleteFile(String name) {
        try {
            FileSystemUtils.deleteRecursively(root.resolve(name));
        } catch (IOException e) {
            log.error(String.format("delete file fail! Cannot find file %s", name));
        }
    }


}
