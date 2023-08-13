package com.example.parking.model.cost;

import com.example.parking.model.ticket.Ticket;

public interface HourlyCost {
    double calculateParkPrice(Ticket ticket);
    double calculateChargePrice(Ticket ticket);
}
