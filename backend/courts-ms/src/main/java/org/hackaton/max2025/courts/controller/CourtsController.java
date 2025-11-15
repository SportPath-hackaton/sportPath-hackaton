package org.hackaton.max2025.courts.controller;

import lombok.RequiredArgsConstructor;
import org.hackaton.max2025.courts.dto.CourtDto;
import org.hackaton.max2025.courts.dto.request.CourtSearchRq;
import org.hackaton.max2025.courts.service.CourtService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/courts")
public class CourtsController {

    private final CourtService courtService;

    @PostMapping("/search/points")
    public ResponseEntity<List<CourtDto>> searchCourts(@RequestBody CourtSearchRq request) {
        List<CourtDto> courts = courtService.getCourtsByCityIdAndCourtTypes(
                request.getCityId(), request.getSports()
        );
        return ResponseEntity.ok(courts);
    }

    @GetMapping
    public ResponseEntity<Page<CourtDto>> getAllCourts(
            @RequestParam(name="page", defaultValue = "1") int pageNumber,
            @RequestParam(name="size", defaultValue = "10") int pageSize
    ) {
        Page<CourtDto> courts = courtService.getAllCourts(pageNumber, pageSize);
        return ResponseEntity.ok(courts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourtDto> getCourtById(@PathVariable UUID id) {
        Optional<CourtDto> court = courtService.getCourtById(id);
        return court.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CourtDto> createCourt(@RequestBody CourtDto courtDto) {
        CourtDto created = courtService.createCourt(courtDto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourtDto> updateCourt(@PathVariable UUID id, @RequestBody CourtDto courtDto) {
        CourtDto updated = courtService.updateCourt(id, courtDto);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourt(@PathVariable UUID id) {
        courtService.deleteCourt(id);
        return ResponseEntity.noContent().build();
    }
}