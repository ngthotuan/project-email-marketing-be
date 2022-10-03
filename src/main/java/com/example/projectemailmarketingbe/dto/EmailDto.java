package com.example.projectemailmarketingbe.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class EmailDto {
    private String email;
}
