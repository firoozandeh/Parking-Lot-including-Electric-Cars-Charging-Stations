package com.example.parking;

import com.example.parking.exception.ChargeStationIsFullException;
import com.example.parking.exception.InvalidChargeStationException;
import com.example.parking.exception.InvalidParkFloorException;
import com.example.parking.exception.ParkingIsFullException;
import com.example.parking.model.park.ParkFloor;
import com.example.parking.model.park.charge.ChargeStation;
import com.example.parking.model.park.gate.EntryGate;
import com.example.parking.model.park.gate.ExitGate;
import com.example.parking.model.park.gate.Gate;
import com.example.parking.model.park.spot.ParkSpot;
import com.example.parking.model.ticket.Ticket;
import com.example.parking.model.vehichle.Vehicle;
import lombok.Getter;
/**
 * Author: Sadegh Firoozandeh
 **/

@Getter
public class Controller {
    private Garage garage = Garage.getInstance();

    /*Operations related to Driver are listed below
     * -onEnterRequest
     * -onExitRequest
     * */
    public Ticket onEnterRequest(Gate entryGate, Vehicle vehicle) throws ParkingIsFullException, InvalidParkFloorException, ChargeStationIsFullException {
        return entryGate.getTicket(vehicle);
    }

    public void onExitRequest(Gate exitGate, Vehicle vehicle) {
        exitGate.payAndExit(vehicle);
    }

    /*Operations related to management are listed below:
     * -addChargeStation
     * -addFloor
     * -addEntryGate
     * -addExitGate
     * -addParkSpot
     * */

    public void addChargeStation(final String floorId, final ChargeStation chargeStation) throws InvalidChargeStationException {
        garage.addChargeStation(floorId, chargeStation);
    }

    public void addFloor(final ParkFloor parkFloor) {
        garage.addFloor(parkFloor);
    }

    public void addEntryGate(final EntryGate entryGate) {
        garage.addEntryGate(entryGate);
    }

    public void addExitGate(final ExitGate exitGate) {
        garage.addExitGate(exitGate);
    }

    public void addParkSpot(final String floorId, final ParkSpot parkSpot) throws InvalidParkFloorException {
        garage.addParkSpot(floorId, parkSpot);
    }


}
