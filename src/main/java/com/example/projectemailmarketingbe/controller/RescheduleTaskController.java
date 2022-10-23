package com.example.projectemailmarketingbe.controller;

import com.example.projectemailmarketingbe.dto.ResponseBodyDto;
import com.example.projectemailmarketingbe.schedule.RescheduleTask;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reschedules")
public class RescheduleTaskController {
    private final RescheduleTask rescheduleTask;

    @PutMapping("/reschedule")
    public ResponseEntity rescheduleTasks() {
        rescheduleTask.rescheduleTasks();
        return ResponseEntity.ok(ResponseBodyDto.builder().data("Config has been reload").statusCode(200).build());
    }
}
