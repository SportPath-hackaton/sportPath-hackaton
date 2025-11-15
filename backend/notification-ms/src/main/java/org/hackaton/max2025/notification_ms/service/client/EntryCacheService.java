package org.hackaton.max2025.notification_ms.service.client;

import org.hackaton.max2025.notification_ms.model.Entry;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface EntryCacheService {
    void cacheEntry(Entry entry);
    void deleteEntryById(UUID entryId);

    List<Entry> getEntriesByTimeRange(Instant startTime, Instant endTime);
}
