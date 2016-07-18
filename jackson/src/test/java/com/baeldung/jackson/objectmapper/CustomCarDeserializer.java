package com.baeldung.jackson.objectmapper;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.jackson.objectmapper.dto.Car;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class CustomCarDeserializer extends JsonDeserializer<Car> {
    private final Logger Logger = LoggerFactory.getLogger(getClass());

    public CustomCarDeserializer() {
    }

    @Override
    public Car deserialize(final JsonParser parser, final DeserializationContext deserializer) throws IOException {
        final Car car = new Car();
        final ObjectCodec codec = parser.getCodec();
        final JsonNode node = codec.readTree(parser);
        try {
            final JsonNode colorNode = node.get("color");
            final String color = colorNode.asText();
            car.setColor(color);
        } catch (final Exception e) {
            Logger.debug("101_parse_exeption: unknown json.");
        }
        return car;
    }
}
