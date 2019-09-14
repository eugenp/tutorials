package com.baeldung.jackson.enums;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.junit.Ignore;
import org.junit.Test;

import com.baeldung.jackson.entities.City;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonEnumSerializationUnitTest {

    @Test
    public final void givenEnum_whenSerializingJson_thenCorrectRepresentation() throws JsonParseException, IOException {
        final String dtoAsString = new ObjectMapper().writeValueAsString(Distance.MILE);

        assertThat(dtoAsString, containsString("1609.34"));
    }

    @Test
    @Ignore
    public final void givenEnum_whenDeserializingJson_thenCorrectRepresentation() throws JsonParseException, IOException {
        String json = "{\"distance\":\"KILOMETER\"}";
        City city = new ObjectMapper().readValue(json, City.class);
        
        assertEquals(Distance.KILOMETER, city.getDistance());
    }

    @Test
    @Ignore
    public final void givenEnumWithJsonValue_whenDeserializingJson_thenCorrectRepresentation() throws JsonParseException, IOException {
        String json =  "{\"JacksonDeserializationUnitTestdistance\": \"0.0254\"}";
        
        City city = new ObjectMapper().readValue(json, City.class);
        assertEquals(Distance.INCH, city.getDistance());
    }
    
    @Test
    public final void givenEnumWithGsonProperty_whenDeserializingJson_thenCorrectRepresentation() throws JsonParseException, IOException {
        String json =  "{\"distance\": \"distance-in-km\"}";
        
        City city = new ObjectMapper().readValue(json, City.class);
        assertEquals(Distance.KILOMETER, city.getDistance());

    }
    
    @Test
    @Ignore
    public final void givenEnumWithGsonCreator_whenDeserializingJson_thenCorrectRepresentation() throws JsonParseException, IOException {
        String json = "{\"distance\": {\"unit\":\"miles\",\"meters\":1609.34}}";

        City city = new ObjectMapper().readValue(json, City.class);
        assertEquals(Distance.MILE, city.getDistance());
        System.out.println(city);
    }
    
}
