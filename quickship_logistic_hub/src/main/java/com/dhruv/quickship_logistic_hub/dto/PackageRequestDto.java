package com.dhruv.quickship_logistic_hub.dto;

import com.dhruv.quickship_logistic_hub.model.PackageDeliveryType;
import com.dhruv.quickship_logistic_hub.model.PackageStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PackageRequestDto {
    @NotBlank
    private String destination;

    @NotNull
    @Min(10)
    private double weight;

    private PackageDeliveryType deliveryType;

    private PackageStatus status = PackageStatus.PENDING;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public PackageDeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(PackageDeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public PackageStatus getStatus() {
        return status;
    }

    public void setStatus(PackageStatus status) {
        this.status = status;
    }
}
