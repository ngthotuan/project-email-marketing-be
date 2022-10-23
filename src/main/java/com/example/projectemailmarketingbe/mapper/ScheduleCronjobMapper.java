package com.example.projectemailmarketingbe.mapper;

import com.example.projectemailmarketingbe.dto.ScheduleCronjobRpDto;
import com.example.projectemailmarketingbe.model.ScheduleCronjobRunEntity;

public interface ScheduleCronjobMapper {
    ScheduleCronjobRpDto ScheduleCronjobRunEntityToRpDto(ScheduleCronjobRunEntity scheduleCronjobRunEntity);
}
