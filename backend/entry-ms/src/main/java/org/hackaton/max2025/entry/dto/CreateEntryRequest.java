package org.hackaton.max2025.entry.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CreateEntryRequest {

    @NotNull(message = "Court ID cannot be null")
    private UUID courtId;

    @NotNull(message = "Entry time cannot be null")
    @Future(message = "Entry time must be in the future")
    private Instant entryTime;
}
