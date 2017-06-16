package com.baeldung.constructor;

import com.baeldung.constructor.FlightRetriever;

public class Reservation {

    private FlightRetriever flightRetriever;

    public Reservation(FlightRetriever flightRetriever) {
        this.flightRetriever = flightRetriever;
    }

    public void displayFlight() {
        flightRetriever.findFlight();
    }

}

