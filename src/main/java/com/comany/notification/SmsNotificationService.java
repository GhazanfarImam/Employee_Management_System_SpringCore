package com.comany.notification;

import org.springframework.stereotype.Component;

@Component
public class SmsNotificationService implements NotificationService{
    @Override
    public void sendNotification(String message) {

        System.out.println("SMS NOTIFICATION");
        System.out.println("Sending sms: " + message);
    }
}
