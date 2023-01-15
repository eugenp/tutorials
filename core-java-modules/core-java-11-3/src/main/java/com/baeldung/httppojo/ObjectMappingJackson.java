package com.baeldung.httppojo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletionException;

public class ObjectMappingJackson {
    List<TodoApp> readValue(String content) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(content, new TypeReference<List<TodoApp>>() {
            });
        } catch (IOException ioe) {
            throw new CompletionException(ioe);
        }
    }
}
