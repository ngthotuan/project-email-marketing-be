package com.example.projectemailmarketingbe.service.Impl;

import com.example.projectemailmarketingbe.dto.StatisticDto;
import com.example.projectemailmarketingbe.repository.EmailRepository;
import com.example.projectemailmarketingbe.repository.ProxyRepository;
import com.example.projectemailmarketingbe.repository.ScheduleRepository;
import com.example.projectemailmarketingbe.repository.TemplateRepository;
import com.example.projectemailmarketingbe.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by ngthotuan on 15/10/2022
 */

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final EmailRepository emailRepository;
    private final TemplateRepository templateRepository;
    private final ProxyRepository proxyRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    public StatisticDto count() {
        return StatisticDto.builder()
                .schedule(scheduleRepository.count())
                .proxy(proxyRepository.count())
                .email(emailRepository.count())
                .template(emailRepository.count())
                .build();
    }
}
