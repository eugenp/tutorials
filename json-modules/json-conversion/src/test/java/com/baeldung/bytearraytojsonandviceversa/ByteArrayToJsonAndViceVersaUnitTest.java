package com.baeldung.bytearraytojsonandviceversa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ByteArrayToJsonAndViceVersaUnitTest {

    byte[] byteArray = {34, 123, 92, 34, 110, 97, 109, 101, 92, 34, 58, 92, 34, 65, 108, 105, 99, 101, 92, 34, 44, 92, 34, 97, 103, 101, 92, 34, 58, 50, 53, 44, 92, 34, 105, 115, 83, 116, 117, 100, 101, 110, 116, 92, 34, 58, 116, 114, 117, 101, 44, 92, 34, 104, 111, 98, 98, 105, 101, 115, 92, 34, 58, 91, 92, 34, 114, 101, 97, 100, 105, 110, 103, 92, 34, 44, 92, 34, 112, 97, 105, 110, 116, 105, 110, 103, 92, 34, 93, 44, 92, 34, 97, 100, 100, 114, 101, 115, 115, 92, 34, 58, 123, 92, 34, 99, 105, 116, 121, 92, 34, 58, 92, 34, 83, 109, 97, 108, 108, 118, 105, 108, 108, 101, 92, 34, 44, 92, 34, 122, 105, 112, 99, 111, 100, 101, 92, 34, 58, 92, 34, 49, 50, 51, 52, 53, 92, 34, 125, 125, 34};
    String jsonString = "{\"name\":\"Alice\",\"age\":25,\"isStudent\":true,\"hobbies\":[\"reading\",\"painting\"],\"address\":{\"city\":\"Smallville\",\"zipcode\":\"12345\"}}";

    @Test
    void givenByteArray_whenConvertingToJsonUsingJackson_thenJsonString() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String actualJsonString = objectMapper.readValue(byteArray, String.class);

        assertEquals(jsonString, actualJsonString);
    }

    @Test
    void givenByteArray_whenConvertingToJsonUsingGson_thenJsonString() {
        Gson gson = new Gson();
        String jsonStringFromByteArray = new String(byteArray, StandardCharsets.UTF_8);
        String actualJsonString = gson.fromJson(jsonStringFromByteArray, String.class);

        assertEquals(jsonString, actualJsonString);
    }

    @Test
    void givenJsonString_whenConvertingToByteArrayUsingJackson_thenByteArray() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] actualByteArray = objectMapper.writeValueAsBytes(jsonString);

        assertEquals(Arrays.toString(byteArray), Arrays.toString(actualByteArray));
    }

    @Test
    void givenJsonString_whenConvertingToByteArrayUsingGson_thenByteArray() {
        Gson gson = new Gson();
        byte[] actualByteArray = gson.toJson(jsonString).getBytes();

        assertEquals(Arrays.toString(byteArray), Arrays.toString(actualByteArray));
    }
}
