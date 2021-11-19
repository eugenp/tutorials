package com.baeldung.jackson.jsonnode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GetAllKeysFromJSONUnitTest {

    private static String json = "{\r\n" + "   \"Name\":\"Craig\",\r\n" + "   \"Age\":10,\r\n" + "   \"BookInterests\":[\r\n" + "      {\r\n" + "         \"Book\":\"The Kite Runner\",\r\n" + "         \"Author\":\"Khaled Hosseini\"\r\n" + "      },\r\n"
        + "      {\r\n" + "         \"Book\":\"Harry Potter\",\r\n" + "         \"Author\":\"J. K. Rowling\"\r\n" + "      }\r\n" + "   ],\r\n" + "   \"FoodInterests\":{\r\n" + "      \"Breakfast\":[\r\n" + "         {\r\n"
        + "            \"Bread\":\"Whole wheat\",\r\n" + "            \"Beverage\":\"Fruit juice\"\r\n" + "         },\r\n" + "         {\r\n" + "            \"Sandwich\":\"Vegetable Sandwich\",\r\n" + "            \"Beverage\":\"Coffee\"\r\n"
        + "         }\r\n" + "      ]\r\n" + "   }\r\n" + "}";

    private static ObjectMapper mapper = new ObjectMapper();

    // Top level keys : [Name, Age, BookInterests, FoodInterests]
    // All keys: [Name, Age, BookInterests, Book, Author, Book, Author, FoodInterests, Breakfast, Bread, Beverage, Sandwich, Beverage]

    @Test
    public void givenAJsonNode_whenUsingFieldNamesMethod_thenWeGetTopFieldNames() {
        List<String> keys = GetAllKeysFromJSON.getKeysInJsonUsingJsonNodeFieldNames(json, mapper);
        assertEquals(4, keys.size());
    }

    @Test
    public void givenAJsonNode_whenUsingFieldNamesMethodForAllNodes_thenWeGetAllFieldNames() {
        List<String> keys = GetAllKeysFromJSON.getAllKeysInJsonUsingJsonNodeFieldNames(json, mapper);
        assertEquals(13, keys.size());
    }

    @Test
    public void givenAJsonNode_whenUsingFieldsMethod_thenWeGetAllFieldNames() {
        List<String> keys = GetAllKeysFromJSON.getAllKeysInJsonUsingJsonNodeFields(json, mapper);
        assertEquals(13, keys.size());
    }

    @Test
    public void givenAJsonNode_whenUsingJsonParserMethod_thenWeGetAllFieldNames() {
        List<String> keys = GetAllKeysFromJSON.getKeysInJsonUsingJsonParser(json, mapper);
        assertEquals(13, keys.size());

        keys = GetAllKeysFromJSON.getKeysInJsonUsingJsonParser(json);
        assertEquals(13, keys.size());
    }

    @Test
    public void givenAJsonNode_whenUsingMaps_thenWeGetAllFieldNames() {
        List<String> keys = GetAllKeysFromJSON.getKeysInJsonUsingMaps(json, mapper);
        assertEquals(13, keys.size());
    }

}
