package com.example.projectemailmarketingbe.schedule;

import com.example.projectemailmarketingbe.model.ScheduleCronjobRunEntity;

import javax.mail.MessagingException;

public abstract class AbstractNotificationScheduledTask {
    abstract void sendNotificationToUser(ScheduleCronjobRunEntity scheduleCronjobRunEntity) throws MessagingException;
}
