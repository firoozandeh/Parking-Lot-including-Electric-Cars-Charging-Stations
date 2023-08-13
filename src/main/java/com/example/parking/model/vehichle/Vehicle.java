package com.example.parking.model.vehichle;

import com.example.parking.model.ticket.Ticket;
import lombok.Getter;

@Getter
public abstract class Vehicle {
    private Ticket ticket;
    protected boolean chargeRequired;
    private final String licenseId;
    private final VehicleType vehicleType;


    public Vehicle(String licenseId, VehicleType vehicleType) {
        this.licenseId = licenseId;
        this.vehicleType = vehicleType;
        this.chargeRequired = false;
    }

}
