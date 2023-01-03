package com.baeldung.java11.httppojo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletionException;

public class ObjectMappingJackson extends ObjectMapper {
    List<TodoApp> readValue(String content) {
        try {
            return this.readValue(content, new TypeReference<List<TodoApp>>() {
            });
        } catch (IOException ioe) {
            throw new CompletionException(ioe);
        }
    }
}