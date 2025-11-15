package org.hackaton.max2025.courts.service.impl;


import lombok.RequiredArgsConstructor;
import org.hackaton.max2025.courts.model.City;
import org.hackaton.max2025.courts.repository.CityRepository;
import org.hackaton.max2025.courts.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public Optional<City> getCityById(Long id) {
        return cityRepository.findById(id);
    }

    public City createCity(City city) {
        return cityRepository.save(city);
    }

    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }

    public Optional<City> getCityByName(String name) {
        return cityRepository.findByName(name);
    }
}