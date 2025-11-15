package org.hackaton.max2025.entry.events.source;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hackaton.max2025.entry.model.Entry;
import org.hackaton.max2025.entry.model.EntryChangeModel;
import org.hackaton.max2025.entry.util.ActionEnum;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class EntryEventSource {

    private final StreamBridge streamBridge;

    public void publishEntryChange(ActionEnum action, Entry entry) {
        log.info("Sending Kafka message {} for Entry ID: {}", action, entry.getId());

        EntryChangeModel change = new EntryChangeModel(
                EntryChangeModel.class.getTypeName(),
                action,
                entry.getId(),
                entry.getUserId(),
                entry.getCourtId(),
                entry.getEntryTime(),
                Instant.now()
        );

        boolean sent = streamBridge.send("entryEvents-out-0",
                MessageBuilder.withPayload(change).build()
        );

        if (sent) {
            log.info("Entry event sent successfully: {} for user {}", action, entry.getUserId());
        } else {
            log.error("Failed to send entry event: {} for user {}", action, entry.getUserId());
        }

    }
}
