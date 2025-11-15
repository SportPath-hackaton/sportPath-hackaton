package org.hackaton.max2025.courts.service;

import org.hackaton.max2025.courts.dto.CourtInfoDto;
import org.hackaton.max2025.courts.dto.response.ActiveEntryCourtInfoRs;
import org.hackaton.max2025.courts.dto.response.HistoryEntryCourtInfoRs;
import org.hackaton.max2025.courts.model.util.CourtType;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourtInfoService {

    List<CourtInfoDto> getAllCourtInfos();

    Optional<CourtInfoDto> getCourtInfoById(UUID id);

    CourtInfoDto createCourtInfo(CourtInfoDto courtInfoDto);

    CourtInfoDto updateCourtInfo(UUID id, CourtInfoDto courtInfoDto);

    void deleteCourtInfo(UUID id);

    List<CourtInfoDto> getCourtInfosByCityId(Long cityId);

    List<CourtInfoDto> getCourtInfosByType(String type);

    List<CourtInfoDto> getCourtInfosByCityIdAndCourtTypes(Long cityId, List<CourtType> courtTypes, int page, int size);

    List<ActiveEntryCourtInfoRs> getCourtsInfoFullByIds(List<UUID> courtIds);

    List<HistoryEntryCourtInfoRs> getCourtsInfoBasicByIds(List<UUID> courtIds);
}