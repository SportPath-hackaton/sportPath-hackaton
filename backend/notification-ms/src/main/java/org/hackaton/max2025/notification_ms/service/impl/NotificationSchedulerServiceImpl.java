package org.hackaton.max2025.notification_ms.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hackaton.max2025.notification_ms.model.Entry;
import org.hackaton.max2025.notification_ms.service.NotificationSchedulerService;
import org.hackaton.max2025.notification_ms.service.NotificationSenderService;
import org.hackaton.max2025.notification_ms.service.client.EntryCacheService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationSchedulerServiceImpl implements NotificationSchedulerService {

    private final EntryCacheService entryCacheService;
    private final NotificationSenderService notificationSenderService;

    @Scheduled(cron = "0 0,30 * * * *") // Каждые 30 минут
    public void checkUpcomingEntries() {
        log.info("Checking for upcoming entries...");

        Instant now = Instant.now();
        Instant threeHoursLater = now.plus(3, ChronoUnit.HOURS);

        // +- 1 мин
        Instant rangeStart = threeHoursLater.minus(5, ChronoUnit.MINUTES);
        Instant rangeEnd = threeHoursLater.plus(5, ChronoUnit.MINUTES);

        List<Entry> upcomingEntries = entryCacheService.getEntriesByTimeRange(rangeStart, rangeEnd);
        log.info("Found {} entries for notification", upcomingEntries.size());

        upcomingEntries.forEach(entry -> {
            try {
                Integer userId = Integer.parseInt(entry.getUserId());

                boolean sent = notificationSenderService.sendNotification(userId, "У вас запись через 3 часа, не забудьте!");

                if (sent) {
                    entryCacheService.deleteEntryById(entry.getId());
                    log.info("Notification sent and entry deleted: {}", entry.getId());
                } else {
                    log.warn("Notification failed for entry: {}, will retry", entry.getId());
                }

            } catch (Exception e) {
                log.error("Error processing entry {}: {}", entry.getId(), e.getMessage());
            }
        });
    }
}