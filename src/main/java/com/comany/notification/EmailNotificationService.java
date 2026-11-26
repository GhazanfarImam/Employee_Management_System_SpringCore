package com.comany.notification;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class EmailNotificationService implements  NotificationService
{
    @Override
    public void sendNotification(String message) {

        System.out.println("EMAIL NOTIFICATION");
        System.out.println("Sending email: " + message);
    }
}
