package com.example.projectemailmarketingbe.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FileRpDto {
    private String message;
    private String name;
    private String originName;
    private String url;
}
