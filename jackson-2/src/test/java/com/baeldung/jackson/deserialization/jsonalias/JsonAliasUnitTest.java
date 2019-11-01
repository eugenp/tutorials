package com.baeldung.jackson.deserialization.jsonalias;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.baeldung.jackson.entities.Weather;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonAliasUnitTest {

    @Test
    public void givenTwoJsonFormats_whenDeserialized_thenWeatherObjectsCreated() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        Weather weather = mapper.readValue("{" + 
            "\"location\": \"London\"," + 
            "\"temp\": 15," + 
            "\"weather\": \"Cloudy\"" + 
            "}", Weather.class);

        assertEquals("London", weather.getLocation());
        assertEquals("Cloudy", weather.getOutlook());
        assertEquals(15, weather.getTemp());

        weather = mapper.readValue("{" + 
            "\"place\": \"Lisbon\"," + 
            "\"temperature\": 35," + 
            "\"outlook\": \"Sunny\"" + 
            "}", Weather.class);

        assertEquals("Lisbon", weather.getLocation());
        assertEquals("Sunny", weather.getOutlook());
        assertEquals(35, weather.getTemp());

    }
}
