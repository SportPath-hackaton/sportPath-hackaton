package org.hackaton.max2025.notification_ms.repository;

import org.hackaton.max2025.notification_ms.model.Entry;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface EntryRedisRepository extends CrudRepository<Entry, UUID> {
}
