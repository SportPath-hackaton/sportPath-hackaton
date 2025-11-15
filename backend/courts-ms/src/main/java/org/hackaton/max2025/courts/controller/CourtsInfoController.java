package org.hackaton.max2025.courts.controller;

import lombok.RequiredArgsConstructor;
import org.hackaton.max2025.courts.dto.CourtInfoDto;
import org.hackaton.max2025.courts.dto.request.ActiveAndHistoryEntryIdsRq;
import org.hackaton.max2025.courts.dto.request.CourtSearchRq;
import org.hackaton.max2025.courts.dto.response.ActiveEntryCourtInfoRs;
import org.hackaton.max2025.courts.dto.response.HistoryEntryCourtInfoRs;
import org.hackaton.max2025.courts.service.CourtInfoService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/courts/info")
public class CourtsInfoController {

    private final CourtInfoService courtInfoService;

    @PostMapping("/search/locations")
    public ResponseEntity<List<CourtInfoDto>> getCourtInfosByCityIdAndCourtTypes(
            @RequestBody CourtSearchRq request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<CourtInfoDto> response = courtInfoService.getCourtInfosByCityIdAndCourtTypes(
                request.getCityId(), request.getSports(), page, size);

        return ResponseEntity.ok(response);
    }

    /*  Ручка без пагинации
    @PostMapping("/search/locations")
    public ResponseEntity<List<CourtInfoDto>> getCourtInfosByCityIdAndCourtTypes(@RequestBody CourtSearchRq request) {
        List<CourtInfoDto> courtInfoDtos = courtInfoService.getCourtInfosByCityIdAndCourtTypes(
                request.getCityId(), request.getSports()
        );
        return ResponseEntity.ok(courtInfoDtos);
    }
     */

    @GetMapping
    public ResponseEntity<List<CourtInfoDto>> getAllCourtInfos() {
        List<CourtInfoDto> courtInfos = courtInfoService.getAllCourtInfos();
        return ResponseEntity.ok(courtInfos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourtInfoDto> getCourtInfoById(@PathVariable UUID id) {
        Optional<CourtInfoDto> courtInfo = courtInfoService.getCourtInfoById(id);
        return courtInfo.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CourtInfoDto> createCourtInfo(@RequestBody CourtInfoDto courtInfoDto) {
        CourtInfoDto created = courtInfoService.createCourtInfo(courtInfoDto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourtInfoDto> updateCourtInfo(@PathVariable UUID id, @RequestBody CourtInfoDto courtInfoDto) {
        CourtInfoDto updated = courtInfoService.updateCourtInfo(id, courtInfoDto);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourtInfo(@PathVariable UUID id) {
        courtInfoService.deleteCourtInfo(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/history/entry")
    public ResponseEntity<List<HistoryEntryCourtInfoRs>> getCourtsInfoBasic(@RequestBody ActiveAndHistoryEntryIdsRq request) {
        List<UUID> courtIds = request.getCourtIds();
        List<HistoryEntryCourtInfoRs> courtInfoList = courtInfoService.getCourtsInfoBasicByIds(courtIds);
        return ResponseEntity.ok(courtInfoList);
    }

    @PostMapping("/active/entry")
    public ResponseEntity<List<ActiveEntryCourtInfoRs>> getCourtsInfoFull(@RequestBody ActiveAndHistoryEntryIdsRq request) {
        List<UUID> courtIds = request.getCourtIds();
        List<ActiveEntryCourtInfoRs> courtInfoList = courtInfoService.getCourtsInfoFullByIds(courtIds);
        return ResponseEntity.ok(courtInfoList);
    }
}