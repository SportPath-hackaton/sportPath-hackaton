package org.hackaton.max2025.courts.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveAndHistoryEntryIdsRq {
    private List<UUID> courtIds;
}
