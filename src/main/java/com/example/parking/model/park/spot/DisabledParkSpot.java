package com.example.parking.model.park.spot;

public class DisabledParkSpot extends ParkSpot{
    public DisabledParkSpot(String id, ParkSpotType parkSpotType) {
        super(id, ParkSpotType.DISABLED);
    }
}
