package org.hackaton.max2025.courts.dto;

import org.hackaton.max2025.courts.model.util.CourtType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourtInfoDto {

    private UUID id;

    private Double lat;

    private Double lon;

    private String address;

    private Double rating;

    private CourtType type;

    private String photoUrl;

    private Boolean paid;

    private String description;

    private String title;

    private Long city_id;
}
