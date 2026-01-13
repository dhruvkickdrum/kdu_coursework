package com.dhruv.hospital_staffing.dto.onboarding;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OnboardTenantRequestDto {
    @NotNull
    @Valid private List<ShiftTypeRequestDto> shiftTypes;
    @NotNull @Valid
    private List<UserRequestDto> users;
    @NotNull @Valid private List<ShiftRequestDto> shifts;
    @NotNull @Valid private List<ShiftAssignmentRequestDto> assignments;

    private boolean triggerFailureAtEnd;
}
