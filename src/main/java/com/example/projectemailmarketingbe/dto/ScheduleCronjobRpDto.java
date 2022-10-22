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
    private String email;
    private String proxyName;
    private String proxyHost;
    private String proxyPort;
    private String templateName;
    private String templateSubject;
    private String scheduleExpression;
    private String emailTos;
    private Boolean enable;
}
