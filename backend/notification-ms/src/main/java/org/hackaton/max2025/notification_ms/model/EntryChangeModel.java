package org.hackaton.max2025.notification_ms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hackaton.max2025.notification_ms.util.ActionEnum;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class EntryChangeModel {
    private String type;
    private ActionEnum action;
    private UUID entryId;
    private String userId;
    private UUID courtId;
    private Instant entryTime;
    private Instant timestamp;
}
