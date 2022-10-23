package com.example.projectemailmarketingbe.schedule;

import com.example.projectemailmarketingbe.model.ScheduleCronjobRunEntity;
import com.example.projectemailmarketingbe.service.ScheduleCronjobService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import static com.example.projectemailmarketingbe.constant.MessageConstant.CRON_EXPRESSION_CONFIG_MESSAGE;

@Slf4j
@Component
@AllArgsConstructor
public class GeneralTaskScheduler {
    @Getter
    private final List<ScheduledFuture<?>> scheduledTasks = new ArrayList<>();
    private final TaskScheduler taskScheduler;
    private final ScheduleCronjobService scheduleCronjobService;
    private final AbstractNotificationScheduledTask scheduledTask;

    public void schedule() {
        scheduledTasks.forEach(scheduledFuture -> scheduledFuture.cancel(false));
        scheduleCronjobService.findAll().forEach(scheduleCronjobRunEntity -> {
            if (scheduleCronjobRunEntity.getEnable()) {
                scheduledTasks.add(notificationDefault(scheduleCronjobRunEntity));
            }
        });
    }

    private ScheduledFuture notificationDefault(ScheduleCronjobRunEntity scheduleCronjobRunEntity) {
        return scheduleSendNotification(scheduleCronjobRunEntity, scheduledTask);
    }

    private ScheduledFuture<?> scheduleSendNotification(ScheduleCronjobRunEntity scheduleCronjobRunEntity, AbstractNotificationScheduledTask scheduledTask) {
        return taskScheduler.schedule(() -> {
                    try {
                        scheduledTask.sendNotificationToUser(scheduleCronjobRunEntity);
                    } catch (MessagingException e) {
                        log.error("scheduleSendNotification FAIL", e);
                    }
                },
                triggerContext -> {
                    var cronExpression = scheduleCronjobRunEntity.getScheduleEntity().getCron();
                    log.info(CRON_EXPRESSION_CONFIG_MESSAGE, cronExpression);
                    return new CronTrigger(cronExpression).nextExecutionTime(triggerContext);
                });
    }
}
