package com.dhruv.hospital_staffing.dto.onboarding;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequestDto {
    @NotBlank
    private String username;
    @NotBlank private String timezone;
    @NotNull
    private Boolean loggedIn;
}
