package com.baeldung.jsonjava;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    void givenJSONWithMixedRepresentationForBoolean_whenParsed_correctBooleanValueReturned() {
        String jsonString = "{\"name\":\"lorem ipsum\",\"active\": 0,\"id\":1}";
        JSONObject jsonObject = new JSONObject(jsonString);
        Object activeObject = jsonObject.get("active");
        if (activeObject instanceof Integer value) {
            assertFalse(value == 1);
        } else if (activeObject instanceof Boolean value) {
              assertFalse(value);
          }
    }
    
    @Test
    void givenJSONString_whenParsedWithGoogleJson_correctBooleanValueReturned() {
        String jsonString = "{\"name\":\"lorem ipsum\",\"active\":true,\"id\":1}";
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        boolean active = jsonObject.get("active").getAsBoolean();
        assertTrue(active);
    }

    @Test
    void givenJSONWithNestedBoolean_whenParsed_correctBooleanValueReturned() {
        String geoJsonString = "{\"type\": \"Feature\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [1.0, 10.0]}, \"properties\": {\"isValid\": true, \"name\": \"Sample Point\"}}";
        JSONObject jsonObject = new JSONObject(geoJsonString);
        JSONObject properties = jsonObject.getJSONObject("properties");
        boolean isValid = properties.getBoolean("isValid");
        assertTrue(isValid);
    }
}
