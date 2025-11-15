package org.hackaton.max2025.notification_ms.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hackaton.max2025.notification_ms.model.Entry;
import org.hackaton.max2025.notification_ms.model.EntryChangeModel;
import org.hackaton.max2025.notification_ms.service.EntryService;
import org.hackaton.max2025.notification_ms.service.client.EntryCacheService;
import org.hackaton.max2025.notification_ms.service.client.EntryCacheServiceImpl;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EntryServiceImpl implements EntryService {

    private final EntryCacheService entryCacheService;

    public void saveEntry(EntryChangeModel entryChangeModel) {
        Entry entry = convertToEntry(entryChangeModel);

        entryCacheService.cacheEntry(entry);
        log.info("Saving entry: {}", entry);
    }

    public void deleteEntry(EntryChangeModel entryChangeModel) {
        entryCacheService.deleteEntryById(entryChangeModel.getEntryId());
        log.info("Deleting entry with id: {}", entryChangeModel.getEntryId());
    }

    private Entry convertToEntry(EntryChangeModel entryChangeModel) {
        return new Entry(
                entryChangeModel.getEntryId(),
                entryChangeModel.getUserId(),
                entryChangeModel.getCourtId(),
                entryChangeModel.getEntryTime()
        );
    }
}
