package org.hackaton.max2025.entry.service;

import org.hackaton.max2025.entry.dto.CreateEntryRequest;
import org.hackaton.max2025.entry.dto.EntryResponse;

import java.util.UUID;

public interface EntryService {
    EntryResponse createEntry(String userId, CreateEntryRequest request);
    void deleteEntry(UUID entryId, String userId);
}
