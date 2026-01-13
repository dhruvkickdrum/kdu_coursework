package com.dhruv.quickship_logistic_hub.service;

import org.springframework.stereotype.Service;
import com.dhruv.quickship_logistic_hub.model.Package;
import com.dhruv.quickship_logistic_hub.model.PackageStatus;
import com.dhruv.quickship_logistic_hub.repository.PackageRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class AnalyticsService {

    private static final BigDecimal PRICE_PER_KG = new BigDecimal("2.50");

    private final PackageRepository repo;

    public AnalyticsService(PackageRepository repo) {
        this.repo = repo;
    }

    public BigDecimal getTotalProjectedRevenue() {
        List<Package> all = repo.getAllPackages();

        BigDecimal total = BigDecimal.ZERO;

        for (Package p : all) {
            // validation and safety checks
            if (p == null) continue;
            if (p.getStatus() == null) continue;
            if (p.getWeight() <= 0) continue;

            if (p.getStatus() == PackageStatus.SORTED) {
                BigDecimal weight = BigDecimal.valueOf(p.getWeight());
                BigDecimal revenue = weight.multiply(PRICE_PER_KG);
                total = total.add(revenue);
            }
        }

        return total.setScale(2, RoundingMode.HALF_UP);
    }
}