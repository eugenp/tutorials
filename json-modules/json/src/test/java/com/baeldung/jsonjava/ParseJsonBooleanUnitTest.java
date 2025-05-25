package com.baeldung.jsonjava;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.json.JSONObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ParseJsonBooleanUnitTest {


    @Test
    void givenJSONString_whenParsed_correctBooleanValueReturned() {
        String jsonString = "{\"name\":\"lorem ipsum\",\"active\":true,\"id\":1}";
        JSONObject jsonObject = new JSONObject(jsonString);
        boolean active = jsonObject.getBoolean("active");
        assertTrue(active);
    }

    @Test
    void givenJSONWithBooleanAs0Or1_whenParsed_correctBooleanValueReturned() {
        String jsonString = "{\"name\":\"lorem ipsum\",\"active\":1,\"id\":1}";
        JSONObject jsonObject = new JSONObject(jsonString);
        int activeInt = jsonObject.getInt("active");
        boolean isActive = (activeInt == 1);
        assertTrue(isActive);
    }

    @Test
    void givenJSONString_whenParsedWithGoogleJson_correctBooleanValueReturned() {
        String jsonString = "{\"name\":\"lorem ipsum\",\"active\":true,\"id\":1}";
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        boolean active = jsonObject.get("active").getAsBoolean();
        assertTrue(active);
    }
     
}
