package com.baeldung.mockito.objectmapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FlowerReader {
    private ObjectMapper objectMapper;

    public FlowerReader(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Flower readFlower(String flowerAsJson) {
        try {
            return objectMapper.readValue(flowerAsJson, Flower.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
