package org.hackaton.max2025.entry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntryResponse {
    private UUID id;
    private String userId;
    private UUID courtId;
    private Instant entryTime;
}
