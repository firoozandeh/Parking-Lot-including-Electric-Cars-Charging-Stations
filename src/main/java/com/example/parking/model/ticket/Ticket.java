package com.example.parking.model.ticket;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
/**
 * Author: Sadegh Firoozandeh
 **/
@Getter
@Builder
public class Ticket {
    private final String id;
    private final String allocatedSpotId;
    private final String allocatedChargeStationId;
    private final LocalDateTime entranceDate;
    private LocalDateTime chargeStartedAt;
    private final String assignedVehicleId;
    @Setter
    private boolean electricChargeFlag;
    @Setter
    private LocalDateTime exitDate;
    @Setter
    private LocalDateTime chargeEndedAt;

    private double amount;

    public void updateChargedAmount(double amount) {
        this.amount = amount;
    }
}
