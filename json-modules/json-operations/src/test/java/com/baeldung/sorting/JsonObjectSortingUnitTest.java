package com.baeldung.sorting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

class JsonObjectSortingUnitTest {

    @Test
    void givenJsonObject_whenUsingJackson_thenSortedBySpeedCorrectly() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        SolarEventContainer container = objectMapper.readValue(
            new File("src/test/resources/solar_events.json"), SolarEventContainer.class);

        List<SolarEvent> events = container.getSolarEvents();
        Collections.sort(events, Comparator.comparingInt(event -> event.getSpeedKmPerS()));

        assertEquals(100, events.get(0)
            .getSpeedKmPerS());
        assertEquals(500, events.get(1)
            .getSpeedKmPerS());
        assertEquals(1000, events.get(2)
            .getSpeedKmPerS());
        assertEquals(1500, events.get(3)
            .getSpeedKmPerS());
    }

    @Test
    public void givenJsonObject_whenUsingGson_thenSortedBySizeCorrectly() throws FileNotFoundException {
        JsonReader reader = new JsonReader(new FileReader("src/test/resources/solar_events.json"));
        JsonElement element = JsonParser.parseReader(reader);
        JsonArray events = element.getAsJsonObject().getAsJsonArray("solar_events");
        List<JsonElement> list = events.asList();

        Collections.sort(list, (a, b) -> {
            double latA = a.getAsJsonObject()
                .getAsJsonObject("coordinates")
                .get("latitude")
                .getAsDouble();
            double latB = b.getAsJsonObject()
                .getAsJsonObject("coordinates")
                .get("latitude")
                .getAsDouble();
            return Double.compare(latA, latB);
        });

        assertEquals(-5, getJsonAttributeAsInt(list.get(0)));
        assertEquals(0, getJsonAttributeAsInt(list.get(1)));
        assertEquals(15, getJsonAttributeAsInt(list.get(2)));
        assertEquals(37, getJsonAttributeAsInt(list.get(3)));
    }

    private int getJsonAttributeAsInt(JsonElement element) {
        return element.getAsJsonObject()
            .getAsJsonObject("coordinates")
            .get("latitude")
            .getAsInt();
    }

}
