package org.hackaton.max2025.entry.service;

import org.hackaton.max2025.entry.dto.ActiveEntryResponse;

public interface ActiveEntriesService {
    ActiveEntryResponse getActiveEntries(String userId, int page, int size);
}
