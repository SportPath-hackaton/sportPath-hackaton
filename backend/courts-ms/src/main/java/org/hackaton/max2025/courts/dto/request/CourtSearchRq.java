package org.hackaton.max2025.courts.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hackaton.max2025.courts.model.util.CourtType;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourtSearchRq {
    private Long cityId;
    private List<CourtType> sports;
}