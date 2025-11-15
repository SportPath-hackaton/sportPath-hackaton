package org.hackaton.max2025.entry.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class HistoryEntry {
    private UUID id;
    private Instant entryTime;
    private String title;
    private String address;
}
