package com.dhruv.hospital_staffing.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class Shift {
    private UUID id;
    private UUID tenantId;
    private UUID shiftTypeId;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
