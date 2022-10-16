package com.example.projectemailmarketingbe.service;

import com.example.projectemailmarketingbe.dto.*;
import com.example.projectemailmarketingbe.model.ScheduleCronjobRunEntity;

import java.util.List;

public interface ScheduleCronjobService {
//    List<ScheduleCronjobRpDto> findAll();
    List<ScheduleCronjobRunEntity> findAll();

    PageResponseDto<ScheduleCronjobRpDto> findAllWithPagingAndSearch(String search, int page, int size);

    ScheduleCronjobRpDto updateProxy(ScheduleCronjobDto scheduleCronjobDto);

    void deleteScheduleCronjobById(Long scheduleCronjobId );

    ScheduleCronjobRpDto findScheduleCronjobById(Long scheduleCronjobId);

    ScheduleCronjobRpDto addScheduleCronjob(ScheduleCronjobDto scheduleCronjobDto);
}
