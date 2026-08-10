package com.baeldung.springai.toolsearchtool;

import org.springframework.ai.tool.annotation.Tool;

import java.util.List;

public class FlightTools {
    @Tool(description = "Searches available flights between two cities")
    public List<FlightOption> searchFlights(String from, String to, String departureDate) {

        return List.of(
          new FlightOption(
             "Romania Airlines",
             from,
             to,
             departureDate,
             249.99
          )
        );
    }
}
