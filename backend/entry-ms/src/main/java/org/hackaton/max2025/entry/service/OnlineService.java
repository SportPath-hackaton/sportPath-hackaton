package org.hackaton.max2025.entry.service;

import org.hackaton.max2025.entry.dto.OnlineResponse;

import java.time.Instant;
import java.util.UUID;

public interface OnlineService {
    OnlineResponse getOnlineUsersByCourtIdAndDateRange(
            UUID courtId,
            Instant startDate,
            Instant endDate
    );
}
