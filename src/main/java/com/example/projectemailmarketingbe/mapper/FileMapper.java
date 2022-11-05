package com.example.projectemailmarketingbe.mapper;

import com.example.projectemailmarketingbe.dto.FileRpDto;
import com.example.projectemailmarketingbe.model.FileEntity;

public interface FileMapper {
    FileRpDto fileEntityToFileRpDto(FileEntity fileEntity);
}
