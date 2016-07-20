package com.baeldung.jackson.objectmapper;

import com.baeldung.jackson.objectmapper.dto.Car;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class TestJavaReadWriteJsonExample {
    final String EXAMPLE_JSON = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
    final String LOCAL_JSON = "[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"BMW\" }]";

    @Test
    public void whenWriteJavaToJson_thanCorrect() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Car car = new Car("yellow", "renault");
        final String carAsString = objectMapper.writeValueAsString(car);
        assertThat(carAsString, containsString("yellow"));
        assertThat(carAsString, containsString("renault"));
    }

    @Test
    public void whenReadJsonToJava_thanCorrect() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Car car = objectMapper.readValue(EXAMPLE_JSON, Car.class);
        assertNotNull(car);
        assertThat(car.getColor(), containsString("Black"));
    }

    @Test
    public void whenReadJsonToJsonNode_thanCorrect() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode jsonNode = objectMapper.readTree(EXAMPLE_JSON);
        assertNotNull(jsonNode);
        assertThat(jsonNode.get("color").asText(), containsString("Black"));
    }

    @Test
    public void whenReadJsonToList_thanCorrect() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        final List<Car> listCar = objectMapper.readValue(LOCAL_JSON, new TypeReference<List<Car>>() {

        });
        for (final Car car : listCar) {
            assertNotNull(car);
            assertThat(car.getType(), equalTo("BMW"));
        }
    }

    @Test
    public void whenReadJsonToMap_thanCorrect() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Map<String, Object> map = objectMapper.readValue(EXAMPLE_JSON, new TypeReference<Map<String, Object>>() {
        });
        assertNotNull(map);
        for (final String key : map.keySet()) {
            assertNotNull(key);
        }
    }
}
