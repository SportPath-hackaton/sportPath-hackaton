package org.hackaton.max2025.entry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OnlineResponse {
    private String message;
    private List<Online> onlines;
}
