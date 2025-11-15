package org.hackaton.max2025.courts.repository;

import org.hackaton.max2025.courts.model.Courts;
import org.hackaton.max2025.courts.model.util.CourtType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourtsRepository extends JpaRepository<Courts, UUID> {
    Optional<Courts> findByCourtInfoId(UUID courtInfoId);

    @Query("SELECT c FROM Courts c " +
            "WHERE c.city.id = :cityId AND c.type IN :courtTypes")
    List<Courts> findByCityIdAndCourtTypesIn(@Param("cityId") Long cityId, @Param("courtTypes") List<CourtType> courtTypes);
}
