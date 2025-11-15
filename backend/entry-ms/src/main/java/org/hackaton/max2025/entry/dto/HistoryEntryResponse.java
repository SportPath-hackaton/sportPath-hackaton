package org.hackaton.max2025.entry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hackaton.max2025.entry.model.HistoryEntry;

import java.util.List;

@Data
@AllArgsConstructor
public class HistoryEntryResponse {
    private String message;
    private List<HistoryEntry> historyEntries;
}
