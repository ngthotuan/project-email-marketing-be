package com.example.projectemailmarketingbe.schedule;

import com.example.projectemailmarketingbe.model.ScheduleCronjobRunEntity;
import org.springframework.stereotype.Component;

@Component
public class DefaultSendPushEmailNotificationTaskImpl extends AbstractNotificationScheduledTask{
    @Override
    void sendNotificationToUser(ScheduleCronjobRunEntity scheduleCronjobRunEntity) {

    }
}
