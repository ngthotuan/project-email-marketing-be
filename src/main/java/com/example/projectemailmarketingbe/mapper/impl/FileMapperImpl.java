package com.example.projectemailmarketingbe.mapper.impl;

import com.example.projectemailmarketingbe.dto.FileRpDto;
import com.example.projectemailmarketingbe.mapper.FileMapper;
import com.example.projectemailmarketingbe.model.FileEntity;
import org.springframework.stereotype.Component;


@Component
public class FileMapperImpl implements FileMapper {
    @Override
    public FileRpDto fileEntityToFileRpDto(FileEntity fileEntity) {
        return FileRpDto.builder()
                .name(fileEntity.getName())
                .originName(fileEntity.getOriginName())
                .url(fileEntity.getFileUrl())
                .build();
    }
}
