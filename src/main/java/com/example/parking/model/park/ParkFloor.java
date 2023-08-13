package com.example.parking.model.park;

import com.example.parking.exception.ChargeStationIsFullException;
import com.example.parking.exception.InvalidParkFloorException;
import com.example.parking.model.park.charge.ChargeStation;
import com.example.parking.model.park.charge.ChargeType;
import com.example.parking.model.park.spot.ParkSpot;
import com.example.parking.model.park.spot.ParkSpotType;
import com.example.parking.model.vehichle.Vehicle;
import com.example.parking.model.vehichle.VehicleType;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Author: Sadegh Firoozandeh
 **/
/*The garage consists of several floors and there are some parking
 * spot and charge stations on each floor.
 * There may be a charge station right next to the parking spot.
 * */

@Getter
public class ParkFloor {
    @Getter
    private final String id;
    private final Map<ParkSpotType, Deque<ParkSpot>> parkSpots;
    private final Map<String, ParkSpot> usedParkSpots;

    private final Map<ChargeType, Deque<ChargeStation>> chargeStations;
    private final Map<String, ChargeStation> usedChargeStations;

    public ParkFloor(String id) {
        this.id = id;
        parkSpots = new HashMap<>();
        parkSpots.put(ParkSpotType.FOSSIl_FUEL_CAR, new ConcurrentLinkedDeque<>());
        parkSpots.put(ParkSpotType.ELECTRIC_CAR, new ConcurrentLinkedDeque<>());
        parkSpots.put(ParkSpotType.HYBRID_CAR, new ConcurrentLinkedDeque<>());
        parkSpots.put(ParkSpotType.DISABLED, new ConcurrentLinkedDeque<>());
        usedParkSpots = new HashMap<>();

        chargeStations = new HashMap<>();
        chargeStations.put(ChargeType.ELECTRIC_CAR, new ConcurrentLinkedDeque<>());
        chargeStations.put(ChargeType.HYBRID_CAR, new ConcurrentLinkedDeque<>());
        chargeStations.put(ChargeType.DISABLED, new ConcurrentLinkedDeque<>());
        usedChargeStations = new HashMap<>();
    }

    public void addChargeStation(final ChargeStation chargeStation) {
        final Optional<ChargeStation> station = chargeStations.get(chargeStation.getChargeType())
                .stream()
                .filter(pS -> pS.getId().equals(chargeStation.getId()))
                .findAny();
        if (station.isPresent())
            return;
        chargeStations.get(chargeStation.getChargeType()).add(chargeStation);
    }
//    To prevent synchronous access to a Charge Station
    public synchronized ChargeStation getChargeStation(final Vehicle vehicle) throws ChargeStationIsFullException {
        final ChargeType chargeType = getChargeTypeForVehicle(vehicle.getVehicleType());
        if (!canCharge(chargeType))
            throw new ChargeStationIsFullException("Cannot Find a Station");
        final ChargeStation chargeStation = chargeStations.get(chargeType).poll();
        chargeStation.assignVehicle(vehicle);
        usedChargeStations.put(chargeStation.getId(), chargeStation);
        return chargeStation;
    }

    public Optional<ChargeStation> freeChargeStation(final String chargeStationId) {
        final ChargeStation chargeStation = usedChargeStations.get(chargeStationId);
        if (Objects.nonNull(chargeStation)) {
            chargeStation.freeStation();
            chargeStations.get(chargeStation.getChargeType()).addFirst(chargeStation);
        }
        return Optional.ofNullable(chargeStation);
    }

    public boolean canCharge(final VehicleType vehicleType) {
        return chargeStations.get(getChargeTypeForVehicle(vehicleType)).size() > 0;
    }

    private boolean canCharge(final ChargeType ChargeTypeForVehicle) {
        return chargeStations.get(ChargeTypeForVehicle).size() > 0;
    }

    private ChargeType getChargeTypeForVehicle(final VehicleType vehicleType) {
        switch (vehicleType) {
            case ELECTRIC_CAR:
                return ChargeType.ELECTRIC_CAR;
            case HYBRID_CAR:
                return ChargeType.HYBRID_CAR;
            default:
                return ChargeType.DISABLED;
        }
    }

    public void addParkSpot(final ParkSpot parkSpot) {
        final Optional<ParkSpot> spot = parkSpots.get(parkSpot.getParkSpotType())
                .stream()
                .filter(pS -> pS.getId().equals(parkSpot.getId()))
                .findAny();
        if (spot.isPresent())
            return;
        parkSpots.get(parkSpot.getParkSpotType()).add(parkSpot);
    }
//    To prevent synchronous access to a parking spot
    public synchronized ParkSpot getParkSpot(final Vehicle vehicle) throws InvalidParkFloorException {
        final ParkSpotType parkSpotType = getParkSpotTypeForVehicle(vehicle.getVehicleType());
        if (!canPark(parkSpotType))
            throw new InvalidParkFloorException("Cannot Find a Spot");
        final ParkSpot parkSpot = parkSpots.get(parkSpotType).poll();
        parkSpot.assignVehicle(vehicle);
        usedParkSpots.put(parkSpot.getId(), parkSpot);
        return parkSpot;
    }

    public Optional<ParkSpot> freeParkSpot(final String parkSpotId) {
        final ParkSpot parkSpot = usedParkSpots.get(parkSpotId);
        if (Objects.nonNull(parkSpot)) {
            parkSpot.freeParkSpot();
            parkSpots.get(parkSpot.getParkSpotType()).addFirst(parkSpot);
        }
        return Optional.ofNullable(parkSpot);
    }

    public boolean canPark(final VehicleType vehicleType) {
        return parkSpots.get(getParkSpotTypeForVehicle(vehicleType)).size() > 0;
    }

    private boolean canPark(final ParkSpotType parkSpotTypeForVehicle) {
        return parkSpots.get(parkSpotTypeForVehicle).size() > 0;
    }

    private ParkSpotType getParkSpotTypeForVehicle(final VehicleType vehicleType) {
        switch (vehicleType) {
            case FOSSIl_FUEL_CAR:
                return ParkSpotType.FOSSIl_FUEL_CAR;
            case ELECTRIC_CAR:
                return ParkSpotType.ELECTRIC_CAR;
            case HYBRID_CAR:
                return ParkSpotType.HYBRID_CAR;
            default:
                return ParkSpotType.DISABLED;
        }
    }

}
