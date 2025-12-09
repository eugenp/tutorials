package com.baeldung.jackson.objectmapper;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.jackson.objectmapper.dto.Car;
import tools.jackson.core.JsonParser;
import tools.jackson.core.ObjectReadContext;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.deser.std.StdDeserializer;

public class CustomCarDeserializer extends StdDeserializer<Car> {

    private static final long serialVersionUID = -5918629454846356161L;
    private final Logger Logger = LoggerFactory.getLogger(getClass());

    public CustomCarDeserializer() {
        super(Car.class);
    }

    public CustomCarDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public Car deserialize(final JsonParser parser, final DeserializationContext deserializer) {
        final Car car = new Car();
        final ObjectReadContext codec = parser.objectReadContext();
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
