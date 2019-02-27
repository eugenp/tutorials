package org.baeldung.gson.deserialization;

import static org.junit.Assert.assertEquals;

import org.baeldung.gson.entities.Weather;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonAlternateTest {

    @Test
    public void testJsonAlias() throws Exception {

        Gson gson = new GsonBuilder().create();

        Weather weather = gson.fromJson("{\n" + "  \"location\": \"London\",\n" + "  \"temp\": 15,\n" + "  \"weather\": \"Cloudy\"\n" + "}", Weather.class);

        assertEquals("London", weather.getLocation());
        assertEquals("Cloudy", weather.getOutlook());
        assertEquals(15, weather.getTemp());

        weather = gson.fromJson("{\n" + "  \"place\": \"Lisbon\",\n" + "  \"temperature\": 35,\n" + "  \"outlook\": \"Sunny\"\n" + "}", Weather.class);

        assertEquals("Lisbon", weather.getLocation());
        assertEquals("Sunny", weather.getOutlook());
        assertEquals(35, weather.getTemp());

    }
}
