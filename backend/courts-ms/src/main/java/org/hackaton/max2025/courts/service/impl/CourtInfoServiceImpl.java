package org.hackaton.max2025.courts.service.impl;

import lombok.RequiredArgsConstructor;
import org.hackaton.max2025.courts.dto.CourtInfoDto;
import org.hackaton.max2025.courts.dto.response.ActiveEntryCourtInfoRs;
import org.hackaton.max2025.courts.dto.response.HistoryEntryCourtInfoRs;
import org.hackaton.max2025.courts.mapper.CourtInfoMapper;
import org.hackaton.max2025.courts.model.CourtInfo;
import org.hackaton.max2025.courts.model.util.CourtType;
import org.hackaton.max2025.courts.repository.CourtInfoRepository;
import org.hackaton.max2025.courts.service.CourtInfoService;
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
public class CourtInfoServiceImpl implements CourtInfoService {

    private final CourtInfoRepository courtInfoRepository;

    public List<CourtInfoDto> getAllCourtInfos() {
        List<CourtInfo> courtInfos = courtInfoRepository.findAll();
        return courtInfos.stream()
                .map(CourtInfoMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public Optional<CourtInfoDto> getCourtInfoById(UUID id) {
        Optional<CourtInfo> courtInfoOpt = courtInfoRepository.findById(id);
        return courtInfoOpt.map(CourtInfoMapper.INSTANCE::toDto);
    }

    public CourtInfoDto createCourtInfo(CourtInfoDto courtInfoDto) {
        CourtInfo courtInfoEntity = CourtInfoMapper.INSTANCE.toEntity(courtInfoDto);
        CourtInfo savedEntity = courtInfoRepository.save(courtInfoEntity);
        return CourtInfoMapper.INSTANCE.toDto(savedEntity);
    }

    public CourtInfoDto updateCourtInfo(UUID id, CourtInfoDto courtInfoDto) {
        Optional<CourtInfo> existingEntityOpt = courtInfoRepository.findById(id);
        if (existingEntityOpt.isPresent()) {
            CourtInfo existingEntity = existingEntityOpt.get();
            existingEntity.setRating(courtInfoDto.getRating());
            existingEntity.setType(courtInfoDto.getType());
            existingEntity.setPhotoUrl(courtInfoDto.getPhotoUrl());
            existingEntity.setPaid(courtInfoDto.getPaid());
            existingEntity.setDescription(courtInfoDto.getDescription());
            existingEntity.setTitle(courtInfoDto.getTitle());

            CourtInfo updatedEntity = courtInfoRepository.save(existingEntity);
            return CourtInfoMapper.INSTANCE.toDto(updatedEntity);
        } else {
            return null;
        }
    }

    public void deleteCourtInfo(UUID id) {
        courtInfoRepository.deleteById(id);
    }

    public List<CourtInfoDto> getCourtInfosByCityId(Long cityId) {
        List<CourtInfo> courtInfos = courtInfoRepository.findByCityId(cityId);
        return courtInfos.stream()
                .map(CourtInfoMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public List<CourtInfoDto> getCourtInfosByType(String type) {
        List<CourtInfo> courtInfos = courtInfoRepository.findByType(type);
        return courtInfos.stream()
                .map(CourtInfoMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public List<CourtInfoDto> getCourtInfosByCityIdAndCourtTypes(Long cityId, List<CourtType> courtTypes, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CourtInfo> courtInfos = courtInfoRepository.findByCityIdAndCourtTypesIn(cityId, courtTypes, pageable);

        return courtInfos.getContent().stream()
                .map(CourtInfoMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public List<HistoryEntryCourtInfoRs> getCourtsInfoBasicByIds(List<UUID> courtIds) {
        if (courtIds == null || courtIds.isEmpty()) {
            return List.of();
        }
        return courtInfoRepository.findCourtInfoBasicByIds(courtIds);
    }

    public List<ActiveEntryCourtInfoRs> getCourtsInfoFullByIds(List<UUID> courtIds) {
        if (courtIds == null || courtIds.isEmpty()) {
            return List.of();
        }
        return courtInfoRepository.findCourtInfoFullByIds(courtIds);
    }
}