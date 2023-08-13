package com.example.parking.model.vehichle;

public class ElectricCar extends Vehicle {
    public ElectricCar(String licenseId) {
        super(licenseId, VehicleType.ELECTRIC_CAR);
        chargeRequired = true;
    }
}
