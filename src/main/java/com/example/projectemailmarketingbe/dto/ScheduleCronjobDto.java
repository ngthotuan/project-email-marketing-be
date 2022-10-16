package com.example.projectemailmarketingbe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleCronjobDto {
    private Long scheduleCronjobId;
    private Long emailId;
    private Long templateId;
    private Long scheduleId;
    private String emailTos;
}
