package com.example.projectemailmarketingbe.mapper.impl;

import com.example.projectemailmarketingbe.dto.ScheduleCronjobRpDto;
import com.example.projectemailmarketingbe.mapper.ScheduleCronjobMapper;
import com.example.projectemailmarketingbe.model.ScheduleCronjobRunEntity;
import org.springframework.stereotype.Component;

@Component
public class ScheduleCronjobMapperImpl implements ScheduleCronjobMapper {
    @Override
    public ScheduleCronjobRpDto ScheduleCronjobRunEntityToRpDto(ScheduleCronjobRunEntity scheduleCronjobRunEntity){
        return ScheduleCronjobRpDto.builder()
                .id(scheduleCronjobRunEntity.getId())
                .email(scheduleCronjobRunEntity.getEmailEntity().getEmail())
                .proxyHost(scheduleCronjobRunEntity.getEmailEntity().getProxyEntity().getHost())
                .proxyName(scheduleCronjobRunEntity.getEmailEntity().getProxyEntity().getName())
                .proxyPort(scheduleCronjobRunEntity.getEmailEntity().getProxyEntity().getPort())
                .templateName(scheduleCronjobRunEntity.getTemplateEntity().getName())
                .templateSubject(scheduleCronjobRunEntity.getTemplateEntity().getSubject())
                .emailTos(scheduleCronjobRunEntity.getEmailTo())
                .scheduleExpression(scheduleCronjobRunEntity.getScheduleEntity().getCron())
                .build();
    }
}
