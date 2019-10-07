package com.baeldung.jackson.deserialization.enums;

import java.io.IOException;
import com.baeldung.jackson.entities.City;
import com.baeldung.jackson.entities.City.CityWithCustomDeserializationEnum.Distance;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class CustomEnumDeserializer extends StdDeserializer<City.CityWithCustomDeserializationEnum.Distance> {

    private static final long serialVersionUID = -1166032307856492833L;

    public CustomEnumDeserializer() {
        this(null);
    }

    public CustomEnumDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public City.CityWithCustomDeserializationEnum.Distance deserialize(final JsonParser jsonParser, final DeserializationContext ctxt) throws IOException, JsonProcessingException {

        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);

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
