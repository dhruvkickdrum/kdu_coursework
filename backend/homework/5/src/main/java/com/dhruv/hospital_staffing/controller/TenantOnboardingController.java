package com.dhruv.hospital_staffing.controller;

import com.dhruv.hospital_staffing.dto.onboarding.OnboardTenantRequestDto;
import com.dhruv.hospital_staffing.service.TenantOnboardingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tenants/{tenantId}")
public class TenantOnboardingController {

    private final TenantOnboardingService onboardingService;

    // Exercise 2: Transactional Batch Save
    @PostMapping("/onboard")
    public ResponseEntity<Map<String, Object>> onboard(
            @PathVariable UUID tenantId,
            @Valid @RequestBody OnboardTenantRequestDto req
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(onboardingService.onboard(tenantId, req));
    }
}
