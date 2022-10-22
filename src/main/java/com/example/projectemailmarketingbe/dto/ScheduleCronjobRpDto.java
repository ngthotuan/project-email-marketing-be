package com.example.projectemailmarketingbe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleCronjobRpDto {
    private Long id;
    private EmailRpDto email;
    private ProxyRpDto proxy;
    private TemplateRpDto template;
    private ScheduleRpDto schedule;
    private String emailTos;
    private Boolean enable;
}
