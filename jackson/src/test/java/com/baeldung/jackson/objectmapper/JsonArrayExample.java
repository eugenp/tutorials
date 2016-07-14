package com.baeldung.jackson.objectmapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.jackson.objectmapper.dto.Car;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonArrayExample extends Example {

    protected final Logger Logger = LoggerFactory.getLogger(getClass());

    public JsonArrayExample() {
    }

    @Override
    public String name() {
        return this.getClass().getName();
    }

    @Override
    public void execute() {
        Logger.debug("Executing: " + name());
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);

            final String jsonCarArray = "[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"FIAT\" }]";
            final Car[] cars = objectMapper.readValue(jsonCarArray, Car[].class);
            for (final Car car : cars) {
                Logger.debug("Color = " + car.getColor());
                Logger.debug("Type = " + car.getType());
            }
        } catch (final Exception e) {
            Logger.error(e.toString());
        }
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);

            final String jsonCarArray = "[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"FIAT\" }]";
            final List<Car> listCar = objectMapper.readValue(jsonCarArray, new TypeReference<List<Car>>() {
            });
            for (final Car car : listCar) {
                Logger.debug("Color = " + car.getColor());
                Logger.debug("Type = " + car.getType());
            }
        } catch (final Exception e) {
            Logger.error(e.toString());
        }
    }

    class Response {

        public Response(final List<Car> cars) {
            this.cars = cars;
        }

        List<Car> cars;

        public List<Car> getCars() {
            return cars;
        }

        public void setCars(final List<Car> cars) {
            this.cars = cars;
        }

    }

}
