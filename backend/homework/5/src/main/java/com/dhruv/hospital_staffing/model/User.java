package com.dhruv.hospital_staffing.model;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    private UUID id;
    private UUID tenantId;
    private String username;
    private boolean loggedIn;
    private String timezone;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
