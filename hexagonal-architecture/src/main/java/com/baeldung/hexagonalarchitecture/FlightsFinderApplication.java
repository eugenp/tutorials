package com.baeldung.hexagonalarchitecture;

import com.baeldung.hexagonalarchitecture.domain.Flight;
import com.baeldung.hexagonalarchitecture.domain.FlightsFinder;
import com.baeldung.hexagonalarchitecture.domain.IFlightsFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlightsFinderApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlightsFinderApplication.class, args);
    }
}
