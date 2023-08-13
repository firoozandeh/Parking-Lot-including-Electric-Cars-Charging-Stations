package com.example.parking.model.park.spot;

import com.example.parking.model.vehichle.Vehicle;
import lombok.Getter;

public abstract class ParkSpot {
    @Getter
    private final String id;
    @Getter
    private final ParkSpotType parkSpotType;
    private boolean isFree;
    private Vehicle assignedVehicle;

    public boolean isFree() {
        return isFree;
    }
    public void setFree(boolean free) {
        isFree = free;
    }

    public ParkSpot(String id, ParkSpotType parkSpotType) {
        this.id = id;
        this.parkSpotType = parkSpotType;
        this.isFree = true;
    }

    public void assignVehicle(Vehicle vehicle) {
        isFree = false;
        assignedVehicle = vehicle;
    }

    public void freeParkSpot() {
        isFree = false;
        assignedVehicle = null;
    }
}
