package com.example.projectemailmarketingbe.schedule;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RescheduleTask {

    private final GeneralTaskSchedule taskScheduler;

    @Scheduled(cron = "${cron.reschedule}")
    public void rescheduleTasks() {
        taskScheduler.schedule();
    }
}
