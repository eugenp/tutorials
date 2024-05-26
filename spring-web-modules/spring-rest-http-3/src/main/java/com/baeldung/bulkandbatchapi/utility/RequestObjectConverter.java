package com.baeldung.bulkandbatchapi.utility;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RequestObjectConverter<T> {

    public T convertJsonObject(JsonNode jsonNode, Class<T> valueType) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonNode.traverse(), valueType);
    }
}
