package org.hackaton.max2025.entry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hackaton.max2025.entry.model.ActiveEntry;

import java.util.List;

@Data
@AllArgsConstructor
public class ActiveEntryResponse {

    private String message;

    private List<ActiveEntry> activeEntries;
}
