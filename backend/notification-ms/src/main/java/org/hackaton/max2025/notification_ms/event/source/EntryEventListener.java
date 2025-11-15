package org.hackaton.max2025.notification_ms.event.source;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hackaton.max2025.notification_ms.model.EntryChangeModel;
import org.hackaton.max2025.notification_ms.service.EntryService;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class EntryEventListener {

    private final EntryService entryService;

    @Bean
    public Consumer<Message<EntryChangeModel>> entryChangeHandler() {
        return message -> {
            EntryChangeModel entryChangeModel = message.getPayload();

            log.info("Received an {} event for entry id {}",
                    entryChangeModel.getAction(), entryChangeModel.getEntryId());

            handleEntryChange(entryChangeModel);
        };
    }

    private void handleEntryChange(EntryChangeModel entryChangeModel) {
        switch (entryChangeModel.getAction()) {
            case CREATED:
                log.info("Handling entry CREATE event for id: {}", entryChangeModel.getEntryId());
                entryService.saveEntry(entryChangeModel);
                break;
            case DELETED:
                log.info("Handling entry DELETE event for id: {}", entryChangeModel.getEntryId());
                entryService.deleteEntry(entryChangeModel);
                break;
            default:
                log.info("Unknown action type: {}", entryChangeModel.getAction());
                break;
        }
    }
}
