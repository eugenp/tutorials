package com.baeldung.jackson.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import org.junit.Test;
import com.baeldung.jackson.entities.City;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonEnumDeserializationUnitTest {

    @Test
    public final void givenEnum_whenDeserializingJson_thenCorrectRepresentation() throws JsonParseException, IOException {
        String json = "{\"distance\":\"KILOMETER\"}";
        City.CityWithDefaultEnum city = new ObjectMapper().readValue(json, City.CityWithDefaultEnum.class);
        
        assertEquals(City.CityWithDefaultEnum.Distance.KILOMETER, city.getDistance());
    }

    @Test
    public final void givenEnumWithJsonValue_whenDeserializingJson_thenCorrectRepresentation() throws JsonParseException, IOException {
        String json =  "{\"distance\": \"0.0254\"}";
        
        City.CityWithJsonValueEnum city = new ObjectMapper().readValue(json, City.CityWithJsonValueEnum.class);
        assertEquals(City.CityWithJsonValueEnum.Distance.INCH, city.getDistance());
    }
    
    @Test
    public final void givenEnumWithJsonProperty_whenDeserializingJson_thenCorrectRepresentation() throws JsonParseException, IOException {
        String json =  "{\"distance\": \"distance-in-km\"}";
        
        City.CityWithJsonPropertyEnum city = new ObjectMapper().readValue(json, City.CityWithJsonPropertyEnum.class);
        assertEquals(City.CityWithJsonPropertyEnum.Distance.KILOMETER, city.getDistance());

    }
    
    @Test
    public final void givenEnumWithJsonCreator_whenDeserializingJson_thenCorrectRepresentation() throws JsonParseException, IOException {
        String json = "{\"distance\": {\"unit\":\"miles\",\"meters\":1609.34}}";

        City.CityWithJsonCreatorEnum city = new ObjectMapper().readValue(json, City.CityWithJsonCreatorEnum.class);
        assertEquals(City.CityWithJsonCreatorEnum.Distance.MILE, city.getDistance());
    }
    
    @Test
    public final void givenEnumWithCustomDeserializer_whenDeserializingJson_thenCorrectRepresentation() throws JsonParseException, IOException {
        String json = "{\"distance\": {\"unit\":\"miles\",\"meters\":1609.34}}";

        City.CityWithCustomDeserializationEnum city = new ObjectMapper().readValue(json, City.CityWithCustomDeserializationEnum.class);
        assertEquals(City.CityWithCustomDeserializationEnum.Distance.MILE, city.getDistance());
    }
}
