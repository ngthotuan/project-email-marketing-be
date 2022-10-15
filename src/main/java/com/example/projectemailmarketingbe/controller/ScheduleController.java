package com.example.projectemailmarketingbe.controller;

import com.example.projectemailmarketingbe.dto.ResponseBodyDto;
import com.example.projectemailmarketingbe.dto.ScheduleDto;
import com.example.projectemailmarketingbe.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Created by ngthotuan on 15/10/2022
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        ResponseBodyDto<List<ScheduleDto>> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(scheduleService.findAll());
        responseBodyDto.setStatusCode(200);
        return ResponseEntity.ok(responseBodyDto);
    }

}
