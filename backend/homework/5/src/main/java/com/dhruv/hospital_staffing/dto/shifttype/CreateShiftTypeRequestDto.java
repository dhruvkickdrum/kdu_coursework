package com.dhruv.hospital_staffing.dto.shifttype;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateShiftTypeRequestDto {
    @NotBlank @Size(max = 100)
    private String name;

    @Size(max = 255)
    private String description;

    @NotNull
    private Boolean active;
}
