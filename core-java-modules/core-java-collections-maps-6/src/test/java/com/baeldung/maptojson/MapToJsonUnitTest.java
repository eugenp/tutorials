package com.baeldung.maptojson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class MapToJsonUnitTest {
@Test
public void given_HashMapData_whenUsingJackson_thenConvertToJson() {
    Map<String, String> data = new HashMap();
    data.put("CS", "Post1");
    data.put("Linux", "Post1");
    data.put("Kotlin", "Post1");
    ObjectMapper objectMapper = new ObjectMapper();
    try {
        objectMapper.writeValueAsString(data);
        Assertions.assertTrue(true);
    } catch (JsonProcessingException e) {
        Assertions.assertFalse(false);
    }
}

@Test
public void given_HashMapData_whenUsingGson_thenConvertToJson() {
    Map<String, String> data = new HashMap<>();
    data.put("CS", "Post1");
    data.put("Linux", "Post1");
    data.put("Kotlin", "Post1");
    Gson gson = new Gson();
    Type typeObject = new TypeToken<HashMap>() {
    }.getType();
    try {
        gson.toJson(data, typeObject);
        Assertions.assertTrue(true);
    } catch (Exception e) {
        Assertions.assertFalse(false);
    }
}

@Test
public void given_HashMapData_whenOrgJson_thenConvertToJsonUsing() {
    Map<String, String> data = new HashMap<>();
    data.put("CS", "Post1");
    data.put("Linux", "Post1");
    data.put("Kotlin", "Post1");
    try {
        JSONObject jsonObject = new JSONObject(data);
        jsonObject.toString();
        Assertions.assertTrue(true);
    } catch (Exception e) {
        Assertions.assertFalse(false);
    }
}
}