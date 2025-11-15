package org.hackaton.max2025.entry.service.impl;

import lombok.RequiredArgsConstructor;
import org.hackaton.max2025.entry.dto.Online;
import org.hackaton.max2025.entry.dto.OnlineResponse;
import org.hackaton.max2025.entry.repository.EntryRepository;
import org.hackaton.max2025.entry.service.OnlineService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OnlineServiceImpl implements OnlineService {

    private final EntryRepository entryRepository;

    public OnlineResponse getOnlineUsersByCourtIdAndDateRange(
            UUID courtId,
            Instant startDate,
            Instant endDate
    ) {
        List<Online> onlines = entryRepository.findOnlineUsersByCourtIdAndDateRange(courtId, startDate, endDate);

        return new OnlineResponse("Online users found successfully", onlines);
    }
}
