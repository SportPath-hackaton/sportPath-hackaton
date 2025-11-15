package org.hackaton.max2025.entry.service.impl;

import lombok.RequiredArgsConstructor;
import org.hackaton.max2025.entry.dto.ActiveEntryResponse;
import org.hackaton.max2025.entry.model.ActiveEntry;
import org.hackaton.max2025.entry.model.ActiveEntryCourtInfo;
import org.hackaton.max2025.entry.model.Entry;
import org.hackaton.max2025.entry.repository.EntryRepository;
import org.hackaton.max2025.entry.service.ActiveEntriesService;
import org.hackaton.max2025.entry.service.client.CourtFeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActiveEntriesServiceImpl implements ActiveEntriesService {

    private final EntryRepository entryRepository;
    private final CourtFeignClient courtFeignClient;

    public ActiveEntryResponse getActiveEntries(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("entryTime").ascending());

        Instant now = Instant.now();

        Page<Entry> entriesPage = entryRepository.findByUserIdAndEntryTimeAfter(
                userId, now, pageable);

        if (entriesPage.getContent().isEmpty()) {
            return new ActiveEntryResponse("No active entries found", Collections.emptyList());
        }

        List<Entry> entries = entriesPage.getContent();

        var courtsId = entries.stream()
                .map(Entry::getCourtId)
                .toList();

        Map<String, List<UUID>> request = new HashMap<>();
        request.put("courtIds", courtsId);

        List<ActiveEntryCourtInfo> courtInfos = courtFeignClient.getActiveEntriesCourtInfo(request);

        Map<UUID, ActiveEntryCourtInfo> courtInfoMap = courtInfos.stream()
                .collect(Collectors.toMap(ActiveEntryCourtInfo::getCourtId, Function.identity()));

        List<ActiveEntry> activeEntries = compareActiveEntries(entries, courtInfoMap);

        return new ActiveEntryResponse("Active entries found successfully", activeEntries);
    }

    private List<ActiveEntry> compareActiveEntries(List<Entry> entries, Map<UUID,
            ActiveEntryCourtInfo> courtInfoMap
    ) {
        List<ActiveEntry> activeEntries = entries.stream()
                .map(entry -> {
                    ActiveEntryCourtInfo courtInfo = courtInfoMap.get(entry.getCourtId());
                    if (courtInfo != null) {
                        return new ActiveEntry(
                                entry.getId(),
                                entry.getEntryTime(),
                                courtInfo.getTitle(),
                                courtInfo.getAddress(),
                                courtInfo.getLat(),
                                courtInfo.getLon()
                        );
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();

        return activeEntries;
    }
}
