package com.dhruv.quickship_logistic_hub.dto;

import java.math.BigDecimal;

public class RevenueResponseDto {
    private BigDecimal totalRevenue;


    public RevenueResponseDto(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}