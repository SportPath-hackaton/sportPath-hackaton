package org.hackaton.max2025.courts.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryEntryCourtInfoRs {
    private UUID courtId;

    private String title;

    private String address;
}
