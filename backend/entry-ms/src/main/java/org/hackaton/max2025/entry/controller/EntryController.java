package org.hackaton.max2025.entry.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hackaton.max2025.entry.dto.*;
import org.hackaton.max2025.entry.service.ActiveEntriesService;
import org.hackaton.max2025.entry.service.EntryService;
import org.hackaton.max2025.entry.service.HistoryEntriesService;
import org.hackaton.max2025.entry.service.OnlineService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/v1/entries")
@RequiredArgsConstructor
public class EntryController {

    private final EntryService entryService;
    private final ActiveEntriesService activeEntriesService;
    private final HistoryEntriesService historyEntriesService;
    private final OnlineService onlineService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntryResponse createEntry(
            @RequestHeader("X-User-Id") String userId,
            @Valid @RequestBody CreateEntryRequest request
    ) {
        return entryService.createEntry(userId, request);
    }

    @DeleteMapping("/{entryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEntry(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable UUID entryId
    ) {
        entryService.deleteEntry(entryId, userId);
    }

    @GetMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    public ActiveEntryResponse getActiveEntries(
            @RequestHeader("X-User-Id") String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
       return activeEntriesService.getActiveEntries(userId, page, size);
    }

    @GetMapping("/history")
    @ResponseStatus(HttpStatus.OK)
    public HistoryEntryResponse getHistoryEntries(
            @RequestHeader("X-User-Id") String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return historyEntriesService.getHistoryEntries(userId, page, size);
    }

    @GetMapping("/online")
    @ResponseStatus(HttpStatus.OK)
    public OnlineResponse getCourtOnline(
            @RequestParam UUID courtId,
            @RequestParam Instant startDate,
            @RequestParam Instant endDate
            ) {
        return onlineService.getOnlineUsersByCourtIdAndDateRange(courtId, startDate, endDate);
    }
}
