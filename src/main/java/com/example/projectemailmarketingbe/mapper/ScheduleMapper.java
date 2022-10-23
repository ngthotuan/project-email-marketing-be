package com.example.projectemailmarketingbe.mapper;

import com.example.projectemailmarketingbe.dto.ScheduleRpDto;
import com.example.projectemailmarketingbe.model.ScheduleEntity;

public interface ScheduleMapper {
    ScheduleRpDto scheduleToScheduleDto(ScheduleEntity scheduleEntity);
}
