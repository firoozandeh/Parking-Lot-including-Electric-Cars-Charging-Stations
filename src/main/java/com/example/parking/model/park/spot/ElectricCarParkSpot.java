package com.example.parking.model.park.spot;

import lombok.Getter;

@Getter
public class ElectricCarParkSpot extends ParkSpot {
    public ElectricCarParkSpot(String id) {
        super(id, ParkSpotType.ELECTRIC_CAR);
    }
}
