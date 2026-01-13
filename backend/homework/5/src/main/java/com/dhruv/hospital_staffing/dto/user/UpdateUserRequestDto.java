package com.dhruv.hospital_staffing.dto.user;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequestDto {
    @Size(max = 120)
    private String username;
    private Boolean loggedIn;
    @Size(max = 64)
    private String timezone;
}
