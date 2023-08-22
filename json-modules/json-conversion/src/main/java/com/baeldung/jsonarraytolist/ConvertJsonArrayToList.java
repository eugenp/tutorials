package com.baeldung.jsonarraytolist;

import java.lang.reflect.Type;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import entities.Product;

public class ConvertJsonArrayToList {

    public List<Product> convertJsonArrayUsingGsonLibrary(String jsonArray) {
        Gson gson = new Gson();

        Type listType = new TypeToken<List<Product>>() {}.getType();

        List<Product> gsonList = gson.fromJson(jsonArray, listType);
        return gsonList;
    }

    public List<Product> convertJsonArrayUsingJacksonLibrary(String jsonArray) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        List<Product> typeReferenceList = objectMapper.readValue(jsonArray, new TypeReference<List<Product>>() {});
        return typeReferenceList;
    }
}
