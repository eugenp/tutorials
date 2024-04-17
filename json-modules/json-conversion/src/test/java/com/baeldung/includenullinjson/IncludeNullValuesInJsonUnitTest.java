package com.baeldung.includenullinjson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IncludeNullValuesInJsonUnitTest {
    String expectedJson = "{\"name\":\"John\",\"address\":null,\"age\":25}";
    Customer obj = new Customer("John", null, 25);

    @Test
    public void givenObjectWithNullField_whenJacksonUsed_thenIncludesNullValue() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        String json = mapper.writeValueAsString(obj);
        assertEquals(expectedJson, json);
    }

    @Test
    public void givenObjectWithNullField_whenGsonUsed_thenIncludesNullValue() {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(obj);
        assertEquals(expectedJson, json);
    }
}
