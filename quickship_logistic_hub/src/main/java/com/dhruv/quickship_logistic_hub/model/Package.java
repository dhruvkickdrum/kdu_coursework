package com.dhruv.quickship_logistic_hub.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Package {
    private String id;

    @NotBlank(message = "Destination cannot be empty.")
    private String destination;

    @NotBlank(message = "Weight cannot be empty")
    @Size(min = 10, max = 100)
    private double weight;
    private PackageStatus status;
    private PackageDeliveryType deliveryType;

    public Package(String destination, double weight, PackageStatus status, PackageDeliveryType deliveryType) {
        this.destination = destination;
        this.weight = weight;
        this.status = status;
        this.deliveryType = deliveryType;
    }

    public Package(String id, String destination, double weight, PackageStatus status, PackageDeliveryType deliveryType) {
        this.id = id;
        this.destination = destination;
        this.weight = weight;
        this.status = status;
        this.deliveryType = deliveryType;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public PackageStatus getStatus() {
        return status;
    }

    public void setStatus(PackageStatus status) {
        this.status = status;
    }

    public PackageDeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(PackageDeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }
}
