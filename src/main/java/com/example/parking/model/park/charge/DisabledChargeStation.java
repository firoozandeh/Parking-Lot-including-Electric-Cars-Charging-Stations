package com.example.parking.model.park.charge;

public class DisabledChargeStation extends ChargeStation{
    public DisabledChargeStation(String id, ChargeType chargeType) {
        super(id, ChargeType.DISABLED);
    }
}
