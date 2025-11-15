package org.hackaton.max2025.courts.repository;

import org.hackaton.max2025.courts.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByName(String name);
}
