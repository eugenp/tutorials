package com.baeldung.jackson.deserialization.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DefaultEnumDeserializationUnitTest {

    @Test
    public void givenEnum_whenDeserializingJson_thenCorrectRepresentation() throws JsonParseException, IOException {
        String json = "{\"distance\":\"KILOMETER\"}";
        City city = new ObjectMapper().readValue(json, City.class);
        
        assertEquals(Distance.KILOMETER, city.getDistance());
    }

}
