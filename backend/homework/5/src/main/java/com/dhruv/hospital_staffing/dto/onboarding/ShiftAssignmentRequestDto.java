package com.dhruv.hospital_staffing.dto.onboarding;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShiftAssignmentRequestDto {
    @NotBlank
    private String username;
    @NotNull
    private Integer shiftIndex;
}
