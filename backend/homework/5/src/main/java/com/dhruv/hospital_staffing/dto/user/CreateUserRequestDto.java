package com.dhruv.hospital_staffing.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserRequestDto {
    @NotBlank @Size(max = 120)
    private String username;

    @NotNull
    private Boolean loggedIn;

    @NotBlank @Size(max = 64)
    private String timezone;
}
