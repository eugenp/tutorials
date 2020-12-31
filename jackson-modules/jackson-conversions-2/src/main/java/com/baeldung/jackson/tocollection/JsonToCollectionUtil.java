package com.baeldung.jackson.tocollection;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonToCollectionUtil {

    private JsonToCollectionUtil(){}

    public static <T> List<T> jsonArrayToList(String json, Class<T> elementClass) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, elementClass);
        return objectMapper.readValue(json, listType);
    }

    public static <T> List<T> jsonArrayToList2(String json, Class<T> elementClass) throws IOException {
        return new ObjectMapper().readValue(json, new TypeReference<List<T>>() {});
    }
}
