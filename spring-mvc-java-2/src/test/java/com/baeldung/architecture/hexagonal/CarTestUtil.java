package com.baeldung.architecture.hexagonal;

import com.baeldung.architecture.hexagonal.core.domain.Car;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CarTestUtil {
    public static String convertCarToJsonString(Car car) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(car);
    }
}
