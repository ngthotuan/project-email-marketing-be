package com.example.projectemailmarketingbe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ngthotuan on 22/10/2022
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateRpMDto {
    private Long id;
    private String name;
    private String subject;
}
