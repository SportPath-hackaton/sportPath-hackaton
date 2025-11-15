package org.hackaton.max2025.entry.service;

import org.hackaton.max2025.entry.dto.HistoryEntryResponse;

public interface HistoryEntriesService {
    HistoryEntryResponse getHistoryEntries(String userId, int page, int size);
}
