package com.baeldung.jackson.map;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonMapDeserializeUnitTest {

    private Map<MyPair, String> map;
    private Map<MyPair, MyPair> cmap;
    final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void whenSimpleMapDeserialize_thenCorrect() throws JsonParseException, JsonMappingException, IOException {

        final String jsonInput = "{\"key\": \"value\"}";
        TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {
        };

        final Map<String, String> map = mapper.readValue(jsonInput, typeRef);

        Assert.assertEquals("value", map.get("key"));
    }

    @Test
    public void whenObjectStringMapDeserialize_thenCorrect() throws JsonParseException, JsonMappingException, IOException {

        final String jsonInput = "{\"Abbott and Costello\":\"Comedy\"}";

        TypeReference<HashMap<MyPair, String>> typeRef = new TypeReference<HashMap<MyPair, String>>() {
        };

        map = mapper.readValue(jsonInput, typeRef);

        Assert.assertEquals("Comedy", map.get(new MyPair("Abbott", "Costello")));

        ClassWithAMap classWithMap = mapper.readValue(jsonInput, ClassWithAMap.class);

        Assert.assertEquals("Comedy", classWithMap.getMap()
            .get(new MyPair("Abbott", "Costello")));
    }

    @Test
    public void whenObjectObjectMapDeserialize_thenCorrect() throws JsonParseException, JsonMappingException, IOException {

        final String jsonInput = "{\"Abbott and Costello\" : \"Comedy and 1940s\"}";
        TypeReference<HashMap<MyPair, MyPair>> typeRef = new TypeReference<HashMap<MyPair, MyPair>>() {
        };

        cmap = mapper.readValue(jsonInput, typeRef);

        Assert.assertEquals(new MyPair("Comedy", "1940s"), cmap.get(new MyPair("Abbott", "Costello")));
    }
}
