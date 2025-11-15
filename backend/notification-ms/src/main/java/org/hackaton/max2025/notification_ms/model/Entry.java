package org.hackaton.max2025.notification_ms.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("entry")
public class Entry {

    private UUID id;
    private String userId;
    private UUID courtId;
    private Instant entryTime;

}