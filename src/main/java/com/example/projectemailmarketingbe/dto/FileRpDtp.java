package com.example.projectemailmarketingbe.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FileRpDtp {
    private String message;
    private String url;
}
