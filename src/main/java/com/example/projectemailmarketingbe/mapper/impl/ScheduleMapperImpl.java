package com.example.projectemailmarketingbe.mapper.impl;

import com.example.projectemailmarketingbe.dto.ScheduleRpDto;
import com.example.projectemailmarketingbe.mapper.ScheduleMapper;
import com.example.projectemailmarketingbe.model.ScheduleEntity;
import org.springframework.stereotype.Component;

/**
 * Created by ngthotuan on 22/10/2022
 */
@Component
public class ScheduleMapperImpl implements ScheduleMapper {
    @Override
    public ScheduleRpDto scheduleToScheduleDto(ScheduleEntity scheduleEntity) {
        return ScheduleRpDto.builder()
                .id(scheduleEntity.getId())
                .name(scheduleEntity.getName())
                .cron(scheduleEntity.getCron())
                .build();
    }
}
