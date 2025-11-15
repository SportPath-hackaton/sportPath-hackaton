package org.hackaton.max2025.notification_ms.service.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hackaton.max2025.notification_ms.model.Entry;
import org.hackaton.max2025.notification_ms.repository.EntryRedisRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class EntryCacheServiceImpl implements EntryCacheService {

    private final EntryRedisRepository entryRedisRepository;

    public void cacheEntry(Entry entry) {
        entryRedisRepository.save(entry);
        log.info("cached entry: {}", entry);
    }

    public void deleteEntryById(UUID entryId) {
        entryRedisRepository.deleteById(entryId);
        log.info("Deleted entry by id: {}", entryId);
    }

    public List<Entry> getEntriesByTimeRange(Instant startTime, Instant endTime) {
        Iterable<Entry> allEntries = entryRedisRepository.findAll();

        List<Entry> result = new ArrayList<>();
        for (Entry entry : allEntries) {
            Instant entryTime = entry.getEntryTime();
            if (!entryTime.isBefore(startTime) && entryTime.isBefore(endTime)) {
                result.add(entry);
            }
        }
        return result;
    }
}
