package com.example.projectemailmarketingbe.schedule;

import com.example.projectemailmarketingbe.model.ScheduleCronjobRunEntity;

public abstract class AbstractNotificationScheduledTask {
    abstract void sendNotificationToUser(ScheduleCronjobRunEntity scheduleCronjobRunEntity);
}
