package com.example.parking.model.park.charge;

import com.example.parking.model.vehichle.Vehicle;
import lombok.Getter;

public abstract class ChargeStation {
    @Getter
    private final String id;
    @Getter
    private final ChargeType chargeType;
    private boolean isFree;
    private Vehicle assignedVehicle;
    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public ChargeStation(String id,ChargeType chargeType) {
        this.id = id;
        this.chargeType = chargeType;
        this.isFree = true;
    }

    public void assignVehicle(Vehicle vehicle) {
        isFree = false;
        assignedVehicle = vehicle;
    }

    public void freeStation() {
        isFree = false;
        assignedVehicle = null;
    }

}
