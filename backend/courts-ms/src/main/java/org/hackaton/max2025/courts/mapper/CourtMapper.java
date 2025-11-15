package org.hackaton.max2025.courts.mapper;

import org.hackaton.max2025.courts.dto.CourtDto;
import org.hackaton.max2025.courts.model.Courts;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CourtMapper {

    CourtMapper INSTANCE = Mappers.getMapper(CourtMapper.class);

    @Mapping(source = "courtInfo.id", target = "courtInfoId")
    CourtDto toDto(Courts court);

    Courts toEntity(CourtDto courtDto);
}