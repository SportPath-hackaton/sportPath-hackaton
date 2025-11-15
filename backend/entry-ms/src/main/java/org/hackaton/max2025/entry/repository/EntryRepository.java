package org.hackaton.max2025.entry.repository;

import org.hackaton.max2025.entry.dto.Online;
import org.hackaton.max2025.entry.model.Entry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface EntryRepository extends JpaRepository<Entry, UUID> {
    boolean existsByUserIdAndEntryTime(String userId, Instant entryTime);

    Page<Entry> findByUserIdAndEntryTimeAfter(String userId, Instant entryTime, Pageable pageable);
    Page<Entry> findByUserIdAndEntryTimeBefore(String userId, Instant entryTime, Pageable pageable);

    @Query("SELECT new org.hackaton.max2025.entry.dto.Online(e.entryTime, COUNT(e)) " +
            "FROM Entry e " +
            "WHERE e.courtId = :courtId " +
            "AND e.entryTime BETWEEN :startDate AND :endDate " +
            "GROUP BY e.entryTime " +
            "ORDER BY e.entryTime")
    List<Online> findOnlineUsersByCourtIdAndDateRange(
            @Param("courtId") UUID courtId,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate);
}
