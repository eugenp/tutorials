package com.baeldung.jackson.enums.deserialization;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class DefaultEnumDeserializationUnitTest {

    @Test
    public void givenEnum_whenDeserializingJson_thenCorrectRepresentation() throws IOException {
        String json = "{\"distance\":\"KILOMETER\"}";
        City city = new ObjectMapper().readValue(json, City.class);

        assertEquals(Distance.KILOMETER, city.getDistance());
    }

    @Test
    public void givenEnum_whenDeserializingJsonWithMapperFeature_thenCorrectRepresentation() throws IOException {
        String json = "{\"distance\":\"KiLoMeTeR\"}";
        ObjectMapper objectMapper = JsonMapper.builder()
            .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
            .build();
        City city = objectMapper.readValue(json, City.class);

        assertEquals(Distance.KILOMETER, city.getDistance());
    }
}
