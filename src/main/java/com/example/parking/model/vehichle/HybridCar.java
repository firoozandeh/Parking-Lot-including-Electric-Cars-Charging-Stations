package com.example.parking.model.vehichle;

public class HybridCar extends Vehicle {
    public HybridCar(String licenseId) {
        super(licenseId, VehicleType.HYBRID_CAR);
        this.chargeRequired = true;
    }
}
