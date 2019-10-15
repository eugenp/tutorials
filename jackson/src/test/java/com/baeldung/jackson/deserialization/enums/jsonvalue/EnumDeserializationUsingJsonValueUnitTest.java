package com.baeldung.jackson.deserialization.enums.jsonvalue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EnumDeserializationUsingJsonValueUnitTest {

    @Test
    public void givenEnumWithJsonValue_whenDeserializingJson_thenCorrectRepresentation() throws JsonParseException, IOException {
        String json =  "{\"distance\": \"0.0254\"}";
        
        City city = new ObjectMapper().readValue(json, City.class);
        assertEquals(Distance.INCH, city.getDistance());
    }
    
}
