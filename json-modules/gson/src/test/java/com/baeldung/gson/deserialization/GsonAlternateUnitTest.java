package com.baeldung.gson.deserialization;

import static org.junit.Assert.assertEquals;

import com.baeldung.gson.entities.Weather;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonAlternateUnitTest {

    @Test
    public void givenTwoJsonFormats_whenDeserialized_thenWeatherObjectsCreated() throws Exception {

        Gson gson = new GsonBuilder().create();

        Weather weather = gson.fromJson("{" + 
            "\"location\": \"London\"," + 
            "\"temp\": 15," + 
            "\"weather\": \"Cloudy\"" + 
            "}", Weather.class);

        assertEquals("London", weather.getLocation());
        assertEquals("Cloudy", weather.getOutlook());
        assertEquals(15, weather.getTemp());

        weather = gson.fromJson("{" + 
            "\"place\": \"Lisbon\"," + 
            "\"temperature\": 35," + 
            "\"outlook\": \"Sunny\"" + 
            "}", Weather.class);

        assertEquals("Lisbon", weather.getLocation());
        assertEquals("Sunny", weather.getOutlook());
        assertEquals(35, weather.getTemp());

    }
}
