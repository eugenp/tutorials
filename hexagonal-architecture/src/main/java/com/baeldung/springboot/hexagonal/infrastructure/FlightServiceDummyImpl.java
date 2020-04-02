package com.baeldung.springboot.hexagonal.infrastructure;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.baeldung.springboot.hexagonal.domain.Flight;

@Component
public class FlightServiceDummyImpl implements FlightService {
    private static final String CDG = "CDG";
    private static Map<String, String> flightCodes = new HashMap<>();

    @Override
    public List<Flight> getRoute(String date, String origin, String destination) {
        String directFlightNumber = flightCodes.get(origin + destination);
        if (directFlightNumber != null) {
            return Arrays.asList(new Flight(directFlightNumber, origin, destination, date));
        } else {
            String flightCode1 = flightCodes.get(origin + CDG);
            String flightCode2 = flightCodes.get(CDG + destination);
            if (flightCode1 != null && flightCode2 != null) {
                return Arrays.asList(new Flight(flightCode1, origin, CDG, date), new Flight(flightCode2, CDG, destination, date));
            }
            ;
        }
        return null;
    }

    static {
        flightCodes.put("NCECDG", "7701");
        flightCodes.put("NCEORY", "7501");
        flightCodes.put("CDGNCE", "7711");
        flightCodes.put("CDGJFK", "005");
        flightCodes.put("JFKCDG", "015");
        flightCodes.put("CDGLAX", "006");
        flightCodes.put("LAXCDG", "016");
    }
}