package com.baeldung.jackson.deserialization.jsonalias;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.baeldung.jackson.entities.Weather;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonAliasTest {

    @Test
    public void testJsonAlias() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        Weather weather = mapper.readValue("{\n" + "  \"location\": \"London\",\n" + "  \"temp\": 15,\n" + "  \"weather\": \"Cloudy\"\n" + "}", Weather.class);

        assertEquals("London", weather.getLocation());
        assertEquals("Cloudy", weather.getOutlook());
        assertEquals(15, weather.getTemp());

        weather = mapper.readValue("{\n" + "  \"place\": \"Lisbon\",\n" + "  \"temperature\": 35,\n" + "  \"outlook\": \"Sunny\"\n" + "}", Weather.class);

        assertEquals("Lisbon", weather.getLocation());
        assertEquals("Sunny", weather.getOutlook());
        assertEquals(35, weather.getTemp());

    }
}
