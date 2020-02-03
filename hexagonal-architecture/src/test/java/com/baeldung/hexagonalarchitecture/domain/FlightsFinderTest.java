package com.baeldung.hexagonalarchitecture.domain;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlightsFinderTest {
    @Mock
    FlightsProvider flightsProvider;

    @InjectMocks
    FlightsFinder flightsFinder;

    @Test
    void whenProvidingFlights_thenFindTheCheapest() throws IOException {
        List<Flight> flights = Lists.list(new Flight("Airline_1", 100), new Flight("Airline_2", 101), new Flight("Airline_3", 99));
        when(flightsProvider.findFlights(anyString(), anyString())).thenReturn(flights);
        Flight cheapestFlight = flightsFinder.checkFlights("origin", "destination");
        assertThat(cheapestFlight.getAirline()).isEqualTo("Airline_3");
        assertThat(cheapestFlight.getPrice()).isEqualTo(99);
    }
}