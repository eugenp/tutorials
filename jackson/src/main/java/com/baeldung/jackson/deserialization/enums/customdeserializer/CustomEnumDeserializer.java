package com.baeldung.jackson.deserialization.enums.customdeserializer;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class CustomEnumDeserializer extends StdDeserializer<Distance> {

    private static final long serialVersionUID = -1166032307856492833L;

    public CustomEnumDeserializer() {
        this(null);
    }

    public CustomEnumDeserializer(Class<?> c) {
        super(c);
    }

    @Override
    public Distance deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        String unit = node.get("unit").asText();
        double meters = node.get("meters").asDouble();

        for (Distance distance : Distance.values()) {
           
            if (distance.getUnit().equals(unit) && 
                Double.compare(distance.getMeters(), meters) == 0) {
                
                return distance;
            }
        }

        return null;
    }

}
