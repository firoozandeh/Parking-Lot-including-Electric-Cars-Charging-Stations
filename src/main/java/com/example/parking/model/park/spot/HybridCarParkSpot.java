package com.example.parking.model.park.spot;

import lombok.Getter;

@Getter
public class HybridCarParkSpot extends ParkSpot {
    public HybridCarParkSpot(String id, ParkSpotType parkSpotType) {
        super(id, ParkSpotType.HYBRID_CAR);
    }
}
