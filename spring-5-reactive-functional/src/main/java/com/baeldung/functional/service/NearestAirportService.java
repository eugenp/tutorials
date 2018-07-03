package com.baeldung.functional.service;

import java.util.HashMap;
import java.util.Map;

public class NearestAirportService {

    private static final Map<String, String> NEAREST_AIRPORT_MAP = new HashMap<String, String>()
    {{
        put("New York", "JFK");
        put("Kolkata", "NSCBIA");
    }};
    
    public String getNearestAirport(String location) {
        return NEAREST_AIRPORT_MAP.get(location);
    }

}
