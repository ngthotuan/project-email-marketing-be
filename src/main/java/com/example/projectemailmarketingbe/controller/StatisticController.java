package com.example.projectemailmarketingbe.controller;

import com.example.projectemailmarketingbe.dto.ResponseBodyDto;
import com.example.projectemailmarketingbe.dto.StatisticDto;
import com.example.projectemailmarketingbe.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ngthotuan on 15/10/2022
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/statistics")
public class StatisticController {
    private final StatisticService statisticService;


    @GetMapping
    public ResponseEntity<?> count() {
        ResponseBodyDto<StatisticDto> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(statisticService.count());
        responseBodyDto.setStatusCode(200);
        return ResponseEntity.ok(responseBodyDto);
    }

}
