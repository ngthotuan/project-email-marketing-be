package com.example.projectemailmarketingbe.schedule;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.example.projectemailmarketingbe.constant.MessageConstant.RESTART_SCHEDULE;

@Component
@AllArgsConstructor
@Slf4j
public class RescheduleTask {

    private final GeneralTaskScheduler taskScheduler;

    @Scheduled(cron = "${cron.reschedule}")
    public void rescheduleTasks() {
        log.info(RESTART_SCHEDULE);
        taskScheduler.schedule();
    }
}
