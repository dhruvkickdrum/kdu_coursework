package com.dhruv.hospital_staffing.dto.onboarding;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ShiftRequestDto {
    @NotBlank
    private String shiftTypeName;
    @NotNull
    private LocalDate dateStart;
    @NotNull private LocalDate dateEnd;
    @NotNull private LocalTime timeStart;
    @NotNull private LocalTime timeEnd;
}
