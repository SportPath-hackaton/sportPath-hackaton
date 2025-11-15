package org.hackaton.max2025.notification_ms.service;

public interface NotificationSenderService {

    boolean sendNotification(Integer userId, String messageText);

}
