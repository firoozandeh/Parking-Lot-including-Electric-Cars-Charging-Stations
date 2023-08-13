package com.example.parking.model.cost;

import com.example.parking.model.ticket.Ticket;
import java.time.Duration;

/**
 * Author: Sadegh Firoozandeh
 **/
public class HourlyCostImpl implements HourlyCost {
    private static final double PARK_PRICE_PER_HOUR = 2.0;
    private static final double CHARGE_PRICE_PER_HOUR = 0.2;

    /*Assume, every started parking hour costs 2 money units.*/

    @Override
    public double calculateParkPrice(final Ticket ticket) {
        final long hoursParked = Duration.between(ticket.getEntranceDate(), ticket.getExitDate()).toHours();
        return PARK_PRICE_PER_HOUR + hoursParked > 0 ? (hoursParked - 1) * PARK_PRICE_PER_HOUR : 0;
    }

    /*Assume, a kWh costs 0.2 money units.*/
    @Override
    public double calculateChargePrice(final Ticket ticket) {
        final long hoursCharged = Duration.between(ticket.getChargeStartedAt(), ticket.getChargeEndedAt()).toHours();
        return CHARGE_PRICE_PER_HOUR + hoursCharged > 0 ? (hoursCharged - 1) * CHARGE_PRICE_PER_HOUR : 0;
    }
}
