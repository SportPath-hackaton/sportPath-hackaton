package org.hackaton.max2025.courts.service;

import org.hackaton.max2025.courts.dto.CourtDto;
import org.hackaton.max2025.courts.model.Courts;
import org.hackaton.max2025.courts.model.util.CourtType;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourtService {

    Page<CourtDto> getAllCourts(int pageNumber, int pageSize);

    Optional<CourtDto> getCourtById(UUID id);

    CourtDto createCourt(CourtDto courtDto);

    CourtDto updateCourt(UUID id, CourtDto courtDto);

    void deleteCourt(UUID id);

    Optional<Courts> getCourtByCourtInfoId(UUID courtInfoId);

    List<CourtDto> getCourtsByCityIdAndCourtTypes(Long cityId, List<CourtType> courtTypes);
}