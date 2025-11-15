package org.hackaton.max2025.entry.service.client;

import org.hackaton.max2025.entry.model.ActiveEntryCourtInfo;
import org.hackaton.max2025.entry.model.HistoryEntryCourtInfo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class CourtFeignClientFallback implements CourtFeignClient {

    @Override
    public List<ActiveEntryCourtInfo> getActiveEntriesCourtInfo(Map<String, List<UUID>> request) {
        return new ArrayList<>();
    }

    @Override
    public List<HistoryEntryCourtInfo> getHistoryEntriesCourtInfo(Map<String, List<UUID>> request) {
        return new ArrayList<>();
    }
}