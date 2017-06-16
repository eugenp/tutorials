package com.baeldung.setter;

import com.baeldung.setter.FlightRetriever;

public class Reservation {

    private FlightRetriever flightRetriever;

    public void setFlight(FlightRetriever flightRetriever) {
        this.flightRetriever = flightRetriever;
    }

    public void displayFlight() {
        flightRetriever.findFlight();
    }

}
