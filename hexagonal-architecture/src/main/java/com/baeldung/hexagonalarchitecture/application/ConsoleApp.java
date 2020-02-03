package com.baeldung.hexagonalarchitecture.application;

import com.baeldung.hexagonalarchitecture.domain.Flight;
import com.baeldung.hexagonalarchitecture.domain.IFlightsFinder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class ConsoleApp implements CommandLineRunner {
    private IFlightsFinder flightsFinder;

    public ConsoleApp(IFlightsFinder flightsFinder) {
        this.flightsFinder = flightsFinder;
    }

    @Override
    public void run(String... args) throws Exception {
        Flight cheapestFlight = this.flightsFinder.checkFlights("zurich", "bangkok");
        System.out.println(String.format("You should book a flight at %s for only %.2f$", cheapestFlight.getAirline(), cheapestFlight.getPrice()));
    }
}
