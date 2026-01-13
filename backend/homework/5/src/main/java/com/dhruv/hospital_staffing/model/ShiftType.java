package com.dhruv.hospital_staffing.model;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class ShiftType {
    private UUID id;
    private UUID tenantId;
    private String name;
    private String description;
    private boolean active;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
