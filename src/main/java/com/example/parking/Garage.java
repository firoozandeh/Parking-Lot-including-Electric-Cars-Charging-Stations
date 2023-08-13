package com.example.parking;


import com.example.parking.exception.ChargeStationIsFullException;
import com.example.parking.exception.InvalidChargeStationException;
import com.example.parking.exception.InvalidParkFloorException;
import com.example.parking.exception.ParkingIsFullException;
import com.example.parking.model.park.ParkFloor;
import com.example.parking.model.park.charge.ChargeStation;
import com.example.parking.model.park.gate.EntryGate;
import com.example.parking.model.park.gate.ExitGate;
import com.example.parking.model.park.spot.ParkSpot;
import com.example.parking.model.vehichle.Vehicle;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Author: Sadegh Firoozandeh
 **/
/*The garage consists of several floors and there are some parking
 * spot and charge stations on each floor.
 * There may be a charge station right next to the parking spot.
 * */
@Getter
public class Garage {
    private final String id;
    private final String name;
    public static final int PARK_CAPACITY = 500;
    public static final int CHARGE_STATION_CAPACITY = 100;
    private final List<ParkFloor> parkFloors;
    private final List<EntryGate> entryGates;
    private final List<ExitGate> exitGates;
    private static Garage garage = null;

    //lazy initialization
    public static Garage getInstance() {
        if (garage == null)
            garage = new Garage();
        return garage;
    }

    private Garage() {
        this.id = UUID.randomUUID().toString();
        this.parkFloors = new ArrayList<>();
        this.entryGates = new ArrayList<>();
        this.exitGates = new ArrayList<>();
        name = "A Place For Everyone";
    }

    /*Related to management*/
    public void addChargeStation(final String floorId, final ChargeStation chargeStation) throws InvalidChargeStationException {
        final Optional<ParkFloor> parkFloor = parkFloors.stream()
                .filter(pF -> pF.getId().equals(floorId))
                .findFirst();
        if (!parkFloor.isPresent())
            throw new InvalidChargeStationException("Charge Station with id " + floorId + " doesn't exists");
        parkFloor.get().addChargeStation(chargeStation);
    }

    /*Related to management*/
    public void addFloor(final ParkFloor parkFloor) {
        final Optional<ParkFloor> floor = parkFloors.stream()
                .filter(pF -> pF.getId().equals(parkFloor.getId()))
                .findFirst();
        if (floor.isPresent())
            return;
        parkFloors.add(parkFloor);
    }

    /*Related to management*/
    public void addEntryGate(final EntryGate entryGate) {
        final Optional<EntryGate> panel = entryGates.stream()
                .filter(eP -> eP.getId().equals(entryGate.getId()))
                .findFirst();
        if (panel.isPresent())
            return;
        entryGates.add(entryGate);
    }

    /*Related to management*/
    public void addExitGate(final ExitGate exitGate) {
        final Optional<ExitGate> panel = exitGates.stream()
                .filter(eP -> eP.getId().equals(exitGate.getId()))
                .findFirst();
        if (panel.isPresent())
            return;
        exitGates.add(exitGate);
    }

    /*Related to management*/
    public void addParkSpot(final String floorId, final ParkSpot parkSpot) throws InvalidParkFloorException {
        final Optional<ParkFloor> parkFloor = parkFloors.stream()
                .filter(pF -> pF.getId().equals(floorId))
                .findFirst();
        if (!parkFloor.isPresent())
            throw new InvalidParkFloorException("Park Floor with id " + floorId + " doesn't exists");
        parkFloor.get().addParkSpot(parkSpot);
    }


    public ParkSpot getParkSpot(final Vehicle vehicle) throws ParkingIsFullException, InvalidParkFloorException {
        final Optional<ParkFloor> parkFloor = parkFloors.stream()
                .filter(pF -> pF.canPark(vehicle.getVehicleType()))
                .findFirst();
        if (!parkFloor.isPresent())
            throw new ParkingIsFullException("** Parking is full **");
        return parkFloor.get().getParkSpot(vehicle);
    }

    public ChargeStation getChargeStation(final Vehicle vehicle) throws ChargeStationIsFullException {
        final Optional<ParkFloor> parkFloor = parkFloors.stream()
                .filter(pF -> pF.canCharge(vehicle.getVehicleType()))
                .findFirst();
        if (!parkFloor.isPresent())
            throw new ChargeStationIsFullException("** Charge Stations is full **");
        return parkFloor.get().getChargeStation(vehicle);
    }

    public void freeParkSpot(final String parkSpotId) {
        parkFloors.stream()
                .filter(parkFloor -> parkFloor.freeParkSpot(parkSpotId).isPresent())
                .findFirst();
    }

    public void freeChargeStation(final String chargeStationId) {
        parkFloors.stream()
                .filter(parkFloor -> parkFloor.freeChargeStation(chargeStationId).isPresent())
                .findFirst();
    }
}
