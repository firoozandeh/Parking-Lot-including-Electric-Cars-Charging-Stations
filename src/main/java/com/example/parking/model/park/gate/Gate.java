package com.example.parking.model.park.gate;

import com.example.parking.exception.ChargeStationIsFullException;
import com.example.parking.exception.InvalidParkFloorException;
import com.example.parking.exception.ParkingIsFullException;
import com.example.parking.model.ticket.Ticket;
import com.example.parking.model.vehichle.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Author: Sadegh Firoozandeh
 **/
@AllArgsConstructor
@Getter
public abstract class Gate {
    private String id;

    public Gate() {
    }

    public abstract void payAndExit(final Vehicle vehicle);

    public abstract Ticket getTicket(final Vehicle vehicle) throws ParkingIsFullException, InvalidParkFloorException, ChargeStationIsFullException;

    // todo
    public abstract boolean open();

    // todo
    public abstract boolean close();

}
