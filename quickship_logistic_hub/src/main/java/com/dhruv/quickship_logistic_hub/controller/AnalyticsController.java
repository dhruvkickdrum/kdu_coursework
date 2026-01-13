package com.dhruv.quickship_logistic_hub.controller;

import com.dhruv.quickship_logistic_hub.dto.RevenueResponseDto;
import com.dhruv.quickship_logistic_hub.service.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    // Controller to get the revenue from all the packages
    @GetMapping("/revenue")
    @PreAuthorize("hasAnyRole('MANAGER','DRIVER')")
    public ResponseEntity<RevenueResponseDto> getRevenue() {
        BigDecimal total = analyticsService.getTotalProjectedRevenue();
        return ResponseEntity.ok(new RevenueResponseDto(total));
    }
}