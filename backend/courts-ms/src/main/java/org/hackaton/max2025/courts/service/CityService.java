package org.hackaton.max2025.courts.service;

import org.hackaton.max2025.courts.model.City;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CityService {

    List<City> getAllCities();

    Optional<City> getCityById(Long id);

    City createCity(City city);

    void deleteCity(Long id);

    Optional<City> getCityByName(String name);
}