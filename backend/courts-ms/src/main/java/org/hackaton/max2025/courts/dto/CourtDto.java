package org.hackaton.max2025.courts.dto;

import org.hackaton.max2025.courts.model.CourtInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hackaton.max2025.courts.model.util.CourtType;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourtDto {

    private UUID id;

    private CourtType type;

    private Double lat;

    private Double lon;

    private UUID courtInfoId;
}