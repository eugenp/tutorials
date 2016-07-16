package com.baeldung.jackson.objectmapper;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.jackson.objectmapper.dto.Car;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonArrayExample extends Example {

    protected final Logger Logger = LoggerFactory.getLogger(getClass());

    public JsonArrayExample() { }

    @Override
    public String name()
    {
        return this.getClass().getName();
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

    final String LOCAL_JSON = "[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"BMW\" }]";

    @Override
    @Test
    public void testExample() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        final Car[] cars = objectMapper.readValue(LOCAL_JSON, Car[].class);
        for (final Car car : cars) {
            assertNotNull(car);
            assertThat(car.getType(), equalTo("BMW"));
        }
        final List<Car> listCar = objectMapper.readValue(LOCAL_JSON, new TypeReference<List<Car>>() {

        });
        for (final Car car : listCar) {
            assertNotNull(car);
            assertThat(car.getType(), equalTo("BMW"));
        }
    }
}
