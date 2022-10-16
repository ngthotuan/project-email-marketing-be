package com.example.projectemailmarketingbe.schedule;

import com.example.projectemailmarketingbe.model.ScheduleCronjobRunEntity;
import com.example.projectemailmarketingbe.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
@RequiredArgsConstructor
public class DefaultSendPushEmailNotificationTaskImpl extends AbstractNotificationScheduledTask{
    private final EmailService emailService;
    @Override
    void sendNotificationToUser(ScheduleCronjobRunEntity scheduleCronjobRunEntity) throws MessagingException {
        emailService.sendMail(scheduleCronjobRunEntity);
    }
}
