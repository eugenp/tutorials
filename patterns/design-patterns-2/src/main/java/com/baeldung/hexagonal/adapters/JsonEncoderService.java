package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.ports.EncoderService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonEncoderService implements EncoderService {
    ObjectMapper objectMapper = new ObjectMapper();

    public String encode(Object data) throws Exception {
        return objectMapper.writeValueAsString(data);
    }
}
