package org.hackaton.max2025.entry.mapper;

import org.hackaton.max2025.entry.dto.CreateEntryRequest;
import org.hackaton.max2025.entry.dto.EntryResponse;
import org.hackaton.max2025.entry.model.Entry;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EntryMapper {

    public Entry toEntity(String userId, CreateEntryRequest request) {
        return Entry.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .courtId(request.getCourtId())
                .entryTime(request.getEntryTime())
                .build();
    }

    public EntryResponse toResponse(Entry entry) {
        return new EntryResponse(
                entry.getId(),
                entry.getUserId(),
                entry.getCourtId(),
                entry.getEntryTime()
        );
    }
}
