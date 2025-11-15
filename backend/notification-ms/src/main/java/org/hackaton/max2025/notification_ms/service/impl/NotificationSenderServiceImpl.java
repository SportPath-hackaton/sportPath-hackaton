package org.hackaton.max2025.notification_ms.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hackaton.max2025.notification_ms.model.MaxMessageRequest;
import org.hackaton.max2025.notification_ms.service.MaxApiService;
import org.hackaton.max2025.notification_ms.service.NotificationSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationSenderServiceImpl implements NotificationSenderService {

    private final MaxApiService maxApiService;

    @Value("${max.bot-token}")
    private String maxToken;

    public boolean sendNotification(Integer userId, String messageText) {
        String authHeader = maxToken;
        MaxMessageRequest request = new MaxMessageRequest(messageText);

        try {
            retrofit2.Response<Void> response = maxApiService.sendMessage(authHeader, userId, request).execute();
            if (response.isSuccessful()) {
                log.info("Notification sent to user {}", userId);
                return true;
            } else {
                log.error("Failed to send notification: HTTP {} - {}",
                        response.code(), response.message());
                if (response.errorBody() != null) {
                    log.error("Error body: {}", response.errorBody().string());
                }
                return false;
            }
        } catch (IOException e) {
            log.error("Error sending notification to user {}: {}", userId, e.getMessage());
            return false;
        }
    }
}
