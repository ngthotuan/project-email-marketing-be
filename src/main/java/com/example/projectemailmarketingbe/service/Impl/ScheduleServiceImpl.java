package com.example.projectemailmarketingbe.service.Impl;

import com.example.projectemailmarketingbe.dto.ScheduleDto;
import com.example.projectemailmarketingbe.exception.NotFoundException;
import com.example.projectemailmarketingbe.model.ScheduleEntity;
import com.example.projectemailmarketingbe.repository.ScheduleRepository;
import com.example.projectemailmarketingbe.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.projectemailmarketingbe.constant.MessageConstant.SCHEDULE_NOT_FOUND;

/**
 * Created by ngthotuan on 15/10/2022
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ScheduleDto> findAll() {
        return scheduleRepository.findAll(Sort.by("createdDate").descending()).stream()
                .map(schedule -> new ModelMapper().map(schedule, ScheduleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleDto updateSchedule(ScheduleDto scheduleDto) {
        ScheduleEntity entity = scheduleRepository.findById(scheduleDto.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Not found schedule with id: %s", scheduleDto.getId())));
        BeanUtils.copyProperties(scheduleDto, entity);
        return modelMapper.map(scheduleRepository.save(entity), ScheduleDto.class);
    }

    @Override
    public ScheduleEntity findById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(() -> new NotFoundException(SCHEDULE_NOT_FOUND));
    }
}
