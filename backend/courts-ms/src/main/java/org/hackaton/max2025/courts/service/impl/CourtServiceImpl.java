package org.hackaton.max2025.courts.service.impl;

import lombok.RequiredArgsConstructor;
import org.hackaton.max2025.courts.dto.CourtDto;
import org.hackaton.max2025.courts.mapper.CourtMapper;
import org.hackaton.max2025.courts.model.Courts;
import org.hackaton.max2025.courts.model.util.CourtType;
import org.hackaton.max2025.courts.repository.CourtsRepository;
import org.hackaton.max2025.courts.service.CourtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourtServiceImpl implements CourtService {

    private final CourtsRepository courtsRepository;

    public Page<CourtDto> getAllCourts(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Courts> courtPage = courtsRepository.findAll(pageable);

        return courtPage.map(CourtMapper.INSTANCE::toDto);
    }

    public Optional<CourtDto> getCourtById(UUID id) {
        Optional<Courts> courtOpt = courtsRepository.findById(id);
        return courtOpt.map(CourtMapper.INSTANCE::toDto);
    }

    public CourtDto createCourt(CourtDto courtDto) {
        Courts courtEntity = CourtMapper.INSTANCE.toEntity(courtDto);
        Courts savedEntity = courtsRepository.save(courtEntity);
        return CourtMapper.INSTANCE.toDto(savedEntity);
    }

    public CourtDto updateCourt(UUID id, CourtDto courtDto) {
        Optional<Courts> existingEntityOpt = courtsRepository.findById(id);
        if (existingEntityOpt.isPresent()) {
            Courts existingEntity = existingEntityOpt.get();
            existingEntity.setLat(courtDto.getLat());
            existingEntity.setLon(courtDto.getLon());

            Courts updatedEntity = courtsRepository.save(existingEntity);
            return CourtMapper.INSTANCE.toDto(updatedEntity);
        } else {
            return null;
        }
    }

    public void deleteCourt(UUID id) {
        courtsRepository.deleteById(id);
    }

    public List<CourtDto> getCourtsByCityIdAndCourtTypes(Long cityId, List<CourtType> courtTypes) {
        List<Courts> courts = courtsRepository.findByCityIdAndCourtTypesIn(cityId, courtTypes);
        return courts.stream()
                .map(CourtMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public Optional<Courts> getCourtByCourtInfoId(UUID courtInfoId) {
        return courtsRepository.findByCourtInfoId(courtInfoId);
    }
}