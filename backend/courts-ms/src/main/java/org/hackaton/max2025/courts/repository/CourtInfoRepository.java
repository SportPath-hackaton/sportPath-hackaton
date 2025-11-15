package org.hackaton.max2025.courts.repository;

import org.hackaton.max2025.courts.dto.response.ActiveEntryCourtInfoRs;
import org.hackaton.max2025.courts.dto.response.HistoryEntryCourtInfoRs;
import org.hackaton.max2025.courts.model.CourtInfo;
import org.hackaton.max2025.courts.model.util.CourtType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourtInfoRepository extends JpaRepository<CourtInfo, UUID> {
    Optional<CourtInfo> findById(UUID courtId);
    List<CourtInfo> findByCityId(Long cityId);
    List<CourtInfo> findByType(String type);

    @Query("SELECT ci FROM CourtInfo ci " +
            "WHERE ci.city.id = :cityId AND ci.type IN :courtTypes")
    Page<CourtInfo> findByCityIdAndCourtTypesIn(@Param("cityId") Long cityId, @Param("courtTypes") List<CourtType> courtTypes, Pageable pageable);

    @Query(value = "SELECT ci.id AS courtId, ci.title AS title, ci.address AS address FROM court_info ci WHERE ci.id IN :courtIds", nativeQuery = true)
    List<HistoryEntryCourtInfoRs> findCourtInfoBasicByIds(@Param("courtIds") List<UUID> courtIds);

    @Query(value = "SELECT ci.id AS courtId, ci.title AS title, ci.address AS address, ci.lat AS lat, ci.lon AS lon FROM court_info ci WHERE ci.id IN :courtIds", nativeQuery = true)
    List<ActiveEntryCourtInfoRs> findCourtInfoFullByIds(@Param("courtIds") List<UUID> courtIds);
}
