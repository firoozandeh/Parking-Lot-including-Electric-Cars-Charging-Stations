package com.example.parking.model.park.gate;

import com.example.parking.exception.ChargeStationIsFullException;
import com.example.parking.exception.InvalidParkFloorException;
import com.example.parking.exception.ParkingIsFullException;
import com.example.parking.Garage;
import com.example.parking.model.park.charge.ChargeStation;
import com.example.parking.model.park.spot.ParkSpot;
import com.example.parking.model.ticket.Ticket;
import com.example.parking.model.vehichle.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
public abstract class EntryGate extends Gate {

    /*driver gets a ticket. If the driver (electric or hybrid car) needs charging, after taking the charge-station will be set
     a flag on his ticket. And another method addChargeTimeToTicket is called to add the charge time to the ticket as well*/

    public Ticket getTicket(final Vehicle vehicle) throws ParkingIsFullException, InvalidParkFloorException, ChargeStationIsFullException {
        Garage garage = Garage.getInstance();
        ParkSpot parkSpot = garage.getParkSpot(vehicle);
        Ticket ticket = generateParkTicket(vehicle, parkSpot.getId());
        EntryGate entryGate = null;
        ChargeStation chargeStation;
        if (vehicle.isChargeRequired()) {
            vehicle.getTicket().setElectricChargeFlag(true);
            chargeStation = garage.getChargeStation(vehicle);
            entryGate.addChargeTimeToTicket(vehicle, chargeStation.getId());
        }
        return ticket;
    }

    private Ticket generateParkTicket(Vehicle vehicle, String allocatedSpotId) {
        return vehicle.getTicket().builder()
                .id(UUID.randomUUID().toString())
                .entranceDate(LocalDateTime.now())
                .assignedVehicleId(vehicle.getLicenseId())
                .allocatedSpotId(allocatedSpotId)
                .build();
    }
    /*This method is called when the car is electric or hybrid and needs charging*/
    private Ticket addChargeTimeToTicket(Vehicle vehicle, String allocatedChargeStationId) {
        return vehicle.getTicket().builder()
                .chargeStartedAt(LocalDateTime.now())
                .allocatedChargeStationId(allocatedChargeStationId)
                .build();
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
