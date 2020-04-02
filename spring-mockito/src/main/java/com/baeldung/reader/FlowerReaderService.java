package com.baeldung.reader;

import com.baeldung.app.api.Flower;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlowerReaderService {

    private ObjectMapper objectMapper;

    public FlowerReaderService(@Autowired ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Flower readFlower(String flowerAsJson) throws JsonProcessingException {
        return objectMapper.readValue(flowerAsJson, Flower.class);
    }
}
