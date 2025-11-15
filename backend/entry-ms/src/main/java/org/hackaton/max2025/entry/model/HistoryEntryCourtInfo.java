package org.hackaton.max2025.entry.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class HistoryEntryCourtInfo {
    private UUID courtId;
    private String title;
    private String address;
}
