package org.hackaton.max2025.courts.mapper;

import org.hackaton.max2025.courts.dto.CourtInfoDto;
import org.hackaton.max2025.courts.model.CourtInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CourtInfoMapper {
    CourtInfoMapper INSTANCE = Mappers.getMapper(CourtInfoMapper.class);

    CourtInfoDto toDto(CourtInfo courtInfo);

    CourtInfo toEntity(CourtInfoDto courtInfoDto);
}
