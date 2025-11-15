package org.hackaton.max2025.entry.service.impl;

import lombok.RequiredArgsConstructor;
import org.hackaton.max2025.entry.dto.HistoryEntryResponse;
import org.hackaton.max2025.entry.model.Entry;
import org.hackaton.max2025.entry.model.HistoryEntry;
import org.hackaton.max2025.entry.model.HistoryEntryCourtInfo;
import org.hackaton.max2025.entry.repository.EntryRepository;
import org.hackaton.max2025.entry.service.HistoryEntriesService;
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
public class HistoryEntriesServiceImpl implements HistoryEntriesService {

    private final EntryRepository entryRepository;
    private final CourtFeignClient courtFeignClient;

    public HistoryEntryResponse getHistoryEntries(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("entryTime").descending());

        Page<Entry> entriesPage = entryRepository.findByUserIdAndEntryTimeBefore(
                userId, Instant.now(), pageable);

        if (entriesPage.getContent().isEmpty()) {
            return new HistoryEntryResponse("No history entries found", Collections.emptyList());
        }

        List<Entry> entries = entriesPage.getContent();

        var courtsId = entries.stream()
                .map(Entry::getCourtId)
                .toList();

        Map<String, List<UUID>> request = new HashMap<>();
        request.put("courtIds", courtsId);

        List<HistoryEntryCourtInfo> courtInfos = courtFeignClient.getHistoryEntriesCourtInfo(request);

        Map<UUID, HistoryEntryCourtInfo> courtInfoMap = courtInfos.stream()
                .collect(Collectors.toMap(HistoryEntryCourtInfo::getCourtId, Function.identity()));

        List<HistoryEntry> historyEntries = compareHistoryEntries(entries, courtInfoMap);

        return new HistoryEntryResponse("History entries found successfully", historyEntries);
    }

    private List<HistoryEntry> compareHistoryEntries(List<Entry> entries,
                                                     Map<UUID, HistoryEntryCourtInfo> courtInfoMap) {
        return entries.stream()
                .map(entry -> {
                    HistoryEntryCourtInfo courtInfo = courtInfoMap.get(entry.getCourtId());
                    if (courtInfo != null) {
                        return new HistoryEntry(
                                entry.getId(),
                                entry.getEntryTime(),
                                courtInfo.getTitle(),
                                courtInfo.getAddress()
                        );
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();
    }
}
