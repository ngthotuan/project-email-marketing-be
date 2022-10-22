package com.example.projectemailmarketingbe.controller;

import com.example.projectemailmarketingbe.dto.PageResponseDto;
import com.example.projectemailmarketingbe.dto.ResponseBodyDto;
import com.example.projectemailmarketingbe.dto.ScheduleCronjobDto;
import com.example.projectemailmarketingbe.dto.ScheduleCronjobRpDto;
import com.example.projectemailmarketingbe.service.ScheduleCronjobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/schedules-cronjobs")
public class ScheduleCronjobController {
    private final ScheduleCronjobService scheduleCronjobService;

    @GetMapping("")
    public ResponseEntity<ResponseBodyDto<PageResponseDto<ScheduleCronjobRpDto>>> findAll(@RequestParam(name = "page",
            required = false,
            defaultValue = "1") int page,
                                                                                          @RequestParam(name = "size",
                                                                                                  required = false,
                                                                                                  defaultValue = "20") int size,
                                                                                          @RequestParam(name = "search",
                                                                                                  defaultValue = "", required =
                                                                                                  false)
                                                                                                  String search) {
        ResponseBodyDto<PageResponseDto<ScheduleCronjobRpDto>> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(scheduleCronjobService.findAllWithPagingAndSearch(search, page - 1, size));
        responseBodyDto.setStatusCode(200);
        return ResponseEntity.ok(responseBodyDto);
    }

    @GetMapping("/{scheduleCronjobId}")
    public ResponseEntity<ResponseBodyDto<ScheduleCronjobRpDto>> findScheduleCronjobById(@PathVariable("scheduleCronjobId") Long scheduleCronjobId) {
        ResponseBodyDto<ScheduleCronjobRpDto> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(scheduleCronjobService.findScheduleCronjobById(scheduleCronjobId));
        responseBodyDto.setStatusCode(200);
        return ResponseEntity.ok(responseBodyDto);
    }

    @DeleteMapping("/{scheduleCronjobId}")
    public ResponseEntity<?> deleteScheduleCronjob(@PathVariable("scheduleCronjobId") Long scheduleCronjobId) {
        scheduleCronjobService.deleteScheduleCronjobById(scheduleCronjobId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/schedules-cronjob")
    public ResponseEntity<?> updateScheduleCronjob(@RequestBody ScheduleCronjobDto scheduleCronjobDto) {
        ResponseBodyDto<ScheduleCronjobRpDto> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(scheduleCronjobService.updateProxy(scheduleCronjobDto));
        responseBodyDto.setStatusCode(200);
        return ResponseEntity.ok(responseBodyDto);
    }

    @PostMapping("/schedules-cronjob")
    public ResponseEntity<?> createScheduleCronjob(@RequestBody ScheduleCronjobDto scheduleCronjobDto) {
        ResponseBodyDto<ScheduleCronjobRpDto> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(scheduleCronjobService.addScheduleCronjob(scheduleCronjobDto));
        responseBodyDto.setStatusCode(201);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBodyDto);
    }

    @PutMapping("/schedules-cronjob/{scheduleCronjobId}/enable")
    public ResponseEntity<?> enableScheduleCronjob(@PathVariable Long scheduleCronjobId) {
        ResponseBodyDto<ScheduleCronjobRpDto> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(scheduleCronjobService.updateStatusCronjob(scheduleCronjobId, Boolean.TRUE));
        responseBodyDto.setStatusCode(200);
        return ResponseEntity.status(HttpStatus.OK).body(responseBodyDto);
    }

    @PutMapping("/schedules-cronjob/{scheduleCronjobId}/disable")
    public ResponseEntity<?> disableScheduleCronjob(@PathVariable Long scheduleCronjobId) {
        ResponseBodyDto<ScheduleCronjobRpDto> responseBodyDto = new ResponseBodyDto<>();
        responseBodyDto.setData(scheduleCronjobService.updateStatusCronjob(scheduleCronjobId, Boolean.FALSE));
        responseBodyDto.setStatusCode(200);
        return ResponseEntity.status(HttpStatus.OK).body(responseBodyDto);
    }
}
