package com.example.projectemailmarketingbe.service.Impl;

import com.example.projectemailmarketingbe.dto.ScheduleDto;
import com.example.projectemailmarketingbe.model.ScheduleEntity;
import com.example.projectemailmarketingbe.repository.ScheduleRepository;
import com.example.projectemailmarketingbe.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ngthotuan on 15/10/2022
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Override
    public List<ScheduleDto> findAll() {
        return scheduleRepository.findAll().stream()
                .map(schedule -> new ModelMapper().map(schedule, ScheduleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean save(ScheduleDto schedule) {
        try {
            scheduleRepository.save(new ModelMapper().map(schedule, ScheduleEntity.class));
            return true;
        } catch (Exception e) {
            log.error("error when save schedule {} ", e.getMessage(), e);
        }
        return false;
    }
}
