package com.example.parking.model.park.spot;

import lombok.Getter;

@Getter
public class FossilFuelCarParkSpot extends ParkSpot {
    public FossilFuelCarParkSpot(String id) {
        super(id, ParkSpotType.FOSSIl_FUEL_CAR);
    }
}
