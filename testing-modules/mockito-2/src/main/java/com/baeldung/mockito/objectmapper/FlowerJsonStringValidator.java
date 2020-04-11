package com.baeldung.mockito.objectmapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FlowerJsonStringValidator {
    private ObjectMapper objectMapper;

    public FlowerJsonStringValidator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public boolean flowerHasPetals(String jsonFlowerAsString) throws JsonProcessingException {
        Flower flower = readFlower(jsonFlowerAsString);
        return flower.getPetals() > 0;
    }

    private Flower readFlower(String flowerAsJson) throws JsonProcessingException {
        return objectMapper.readValue(flowerAsJson, Flower.class);
    }
}
