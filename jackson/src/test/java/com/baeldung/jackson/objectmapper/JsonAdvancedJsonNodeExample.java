package com.baeldung.jackson.objectmapper;

import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.jackson.objectmapper.dto.Car;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonAdvancedJsonNodeExample extends Example {

    protected final Logger Logger = LoggerFactory.getLogger(getClass());

    public JsonAdvancedJsonNodeExample() {
    }

    String jsonString = "{ \"color\" : \"Black\", \"type\" : \"Fiat\", \"year\" : \"1970\" }";

    @Override
    public String name() {
        return this.getClass().getName();
    }

    @Override
    public void execute() {
        Logger.debug("Executing: " + name());
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            final Car car = objectMapper.readValue(jsonString, Car.class);
            final JsonNode jsonNodeRoot = objectMapper.readTree(jsonString);
            final JsonNode jsonNodeYear = jsonNodeRoot.get("year");
            final String year = jsonNodeYear.asText();
            Logger.debug("Year = " + year);
            Logger.debug("Color = " + car.getColor());
            Logger.debug("Type = " + car.getType());

            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            final StringWriter string = new StringWriter();
            objectMapper.writeValue(string, car);
            Logger.debug("Car JSON is:" + string);
        } catch (final Exception e) {
            Logger.error(e.toString());
        }
    }

}
