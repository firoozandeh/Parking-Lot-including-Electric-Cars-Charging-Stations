package com.example.parking.model.park.gate;

import com.example.parking.model.cost.HourlyCost;
import com.example.parking.Garage;
import com.example.parking.model.ticket.Ticket;
import com.example.parking.model.vehichle.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public abstract class ExitGate extends Gate {
    private final HourlyCost hourlyCost;

    /*At the ExitGate, the operator checks Charge-Flag on the ticket, if it is set,
      adds the charging fee to the parking fee.*/
    public void payAndExit(final Vehicle vehicle) {
        Ticket ticket = vehicle.getTicket();
        Garage garage = Garage.getInstance();
        if (ticket.isElectricChargeFlag()) {
            garage.freeChargeStation(ticket.getAllocatedChargeStationId());
            ticket.setChargeEndedAt(LocalDateTime.now());
            ticket.updateChargedAmount(hourlyCost.calculateChargePrice(ticket));
            garage.freeParkSpot(ticket.getAllocatedSpotId());
            ticket.setExitDate(LocalDateTime.now());
            ticket.updateChargedAmount(hourlyCost.calculateParkPrice(ticket));
            return;
        }
        garage.freeParkSpot(ticket.getAllocatedSpotId());
        ticket.setExitDate(LocalDateTime.now());
        ticket.updateChargedAmount(hourlyCost.calculateParkPrice(ticket));
    }

    @Override
    public boolean open() {
        return true;
    }

    @Override
    public boolean close() {
        return true;
    }

}
