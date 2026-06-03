package com.example.spacecenter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SatelliteEvent {
    private String eventId;
    private String aggregateId;
    private String eventType;
    private Object payload;
}
