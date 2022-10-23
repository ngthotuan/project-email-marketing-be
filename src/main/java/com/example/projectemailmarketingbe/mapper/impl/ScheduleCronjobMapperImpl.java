package com.example.projectemailmarketingbe.mapper.impl;

import com.example.projectemailmarketingbe.dto.ScheduleCronjobRpDto;
import com.example.projectemailmarketingbe.mapper.*;
import com.example.projectemailmarketingbe.model.ScheduleCronjobRunEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduleCronjobMapperImpl implements ScheduleCronjobMapper {
    private final EmailMapper emailMapper;
    private final ProxyMapper proxyMapper;
    private final TemplateMapper templateMapper;
    private final ScheduleMapper scheduleMapper;

    @Override
    public ScheduleCronjobRpDto ScheduleCronjobRunEntityToRpDto(ScheduleCronjobRunEntity scheduleCronjobRunEntity) {
        return ScheduleCronjobRpDto.builder()
                .id(scheduleCronjobRunEntity.getId())
                .email(emailMapper.emailToEmailDto(scheduleCronjobRunEntity.getEmailEntity()))
                .proxy(scheduleCronjobRunEntity.getEmailEntity().getProxyEntity()!=null?
                        proxyMapper.proxyEntityToProxyRpDto(scheduleCronjobRunEntity.getEmailEntity().getProxyEntity())
                        :null)
                .template(templateMapper.templateEntityToTemplateRpDto(scheduleCronjobRunEntity.getTemplateEntity()))
                .schedule(scheduleMapper.scheduleToScheduleDto(scheduleCronjobRunEntity.getScheduleEntity()))
                .emailTos(scheduleCronjobRunEntity.getEmailTo())
                .enable(scheduleCronjobRunEntity.getEnable())
                .build();
    }
}
