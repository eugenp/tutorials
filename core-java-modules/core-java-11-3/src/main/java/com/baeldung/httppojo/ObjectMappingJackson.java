package com.baeldung.httppojo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletionException;

public class ObjectMappingJackson {
    ObjectMapper objectMapper = new ObjectMapper();

    List<Todo> readValue(String content) {

        try {
            return objectMapper.readValue(content, new TypeReference<List<Todo>>() {
            });
        } catch (IOException ioe) {
            throw new CompletionException(ioe);
        }
    }
}
