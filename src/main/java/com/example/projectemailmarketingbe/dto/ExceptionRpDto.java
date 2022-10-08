package com.example.projectemailmarketingbe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionRpDto {
    private Date date;
    private String message;
    private String details;
}
