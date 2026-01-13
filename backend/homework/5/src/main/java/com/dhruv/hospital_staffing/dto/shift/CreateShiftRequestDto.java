package com.dhruv.hospital_staffing.dto.shift;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class CreateShiftRequestDto {
    @NotNull
    private UUID shiftTypeId;
    @NotNull private LocalDate dateStart;
    @NotNull private LocalDate dateEnd;
    @NotNull private LocalTime timeStart;
    @NotNull private LocalTime timeEnd;
}
