package org.hackaton.max2025.entry.service.client;

import org.hackaton.max2025.entry.model.ActiveEntryCourtInfo;
import org.hackaton.max2025.entry.model.HistoryEntryCourtInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@FeignClient(
        name = "CourtFeignClient",
        url = "http://courts-service:8083",
        fallback = CourtFeignClientFallback.class
)
public interface CourtFeignClient {

    @PostMapping("/v1/courts/info/active/entry")
    List<ActiveEntryCourtInfo> getActiveEntriesCourtInfo(@RequestBody Map<String, List<UUID>> request);

    @PostMapping("/v1/courts/info/history/entry")
    List<HistoryEntryCourtInfo> getHistoryEntriesCourtInfo(@RequestBody Map<String, List<UUID>> request);
}
