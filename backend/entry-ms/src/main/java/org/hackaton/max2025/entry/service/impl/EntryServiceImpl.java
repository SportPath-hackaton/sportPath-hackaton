package org.hackaton.max2025.entry.service.impl;

import lombok.RequiredArgsConstructor;
import org.hackaton.max2025.entry.dto.CreateEntryRequest;
import org.hackaton.max2025.entry.dto.EntryResponse;
import org.hackaton.max2025.entry.events.source.EntryEventSource;
import org.hackaton.max2025.entry.exception.AccessDeniedException;
import org.hackaton.max2025.entry.exception.DoubleEntryException;
import org.hackaton.max2025.entry.exception.EntryNotFoundException;
import org.hackaton.max2025.entry.mapper.EntryMapper;
import org.hackaton.max2025.entry.model.Entry;
import org.hackaton.max2025.entry.repository.EntryRepository;
import org.hackaton.max2025.entry.service.EntryService;
import org.hackaton.max2025.entry.util.ActionEnum;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EntryServiceImpl implements EntryService {

    private final EntryRepository entryRepository;
    private final EntryEventSource entryEventSource;
    private final EntryMapper entryMapper;

    public EntryResponse createEntry(String userId, CreateEntryRequest request) {
        validateUserNotDoubleEntry(userId, request.getEntryTime());

        Entry entry = entryMapper.toEntity(userId, request);
        Entry savedEntry = entryRepository.save(entry);
        entryEventSource.publishEntryChange(ActionEnum.CREATED, savedEntry);
        return entryMapper.toResponse(savedEntry);
    }


    public void deleteEntry(UUID entryId, String userId) {
        Entry entry = entryRepository.findById(entryId)
                .orElseThrow(() -> new EntryNotFoundException("Entry not found with id: " + entryId));

        if (!entry.getUserId().equals(userId)) {
            throw new AccessDeniedException("You can only delete your own entries");
        }

        entryRepository.delete(entry);
        entryEventSource.publishEntryChange(ActionEnum.DELETED, entry);
    }


    private void validateUserNotDoubleEntry(String userId, Instant entryTime) {
        boolean hasExistingEntry = entryRepository.existsByUserIdAndEntryTime(userId, entryTime);
        if (hasExistingEntry) {
            throw new DoubleEntryException("User already has an entry at this time");
        }
    }
}
