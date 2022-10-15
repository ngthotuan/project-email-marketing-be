package com.example.projectemailmarketingbe.service;

import com.example.projectemailmarketingbe.dto.ScheduleDto;

import java.util.List;

/**
 * Created by ngthotuan on 15/10/2022
 */
public interface ScheduleService {
    // don't need to pagination
    List<ScheduleDto> findAll();

    boolean save(ScheduleDto schedule);
}
