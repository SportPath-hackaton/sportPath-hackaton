package org.hackaton.max2025.entry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class Online {
    private Instant entryTime;
    private Long usersCount;
}
