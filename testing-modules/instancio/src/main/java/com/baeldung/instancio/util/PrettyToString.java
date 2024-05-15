package com.baeldung.instancio.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class PrettyToString {

    private static final ObjectWriter objectWriter = new ObjectMapper()
            .registerModules(new JavaTimeModule())
            .writerWithDefaultPrettyPrinter();

    public static String toPrettyString(Object obj) {
        try {
            return objectWriter.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
