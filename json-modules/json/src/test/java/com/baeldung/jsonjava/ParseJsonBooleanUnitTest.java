package com.baeldung.jsonjava;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.assertj.core.api.Assertions.assertThat;
import org.json.JSONObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ParseJsonBooleanUnitTest {

    private final String json = "{\"name\":\"lorem ipsum\",\"active\":true,\"id\":1}";
    
    @Test
    void givenJSONString_whenParsed_correctBooleanValueReturned() {
        JSONObject jsonObject = new JSONObject(json);
        boolean active = jsonObject.getBoolean("active");
        assertTrue(active);
    }

    @Test
    void givenJSONWithBooleanAs0Or1_whenParsed_correctBooleanValueReturned() {
        JSONObject jsonObject = new JSONObject(json);
        assertThat(jsonObject.getInt("active").isEqualTo(1);
    }

    @Test
    void givenJSONWithMixedRepresentationForBoolean_whenParsed_correctBooleanValueReturned() {
        JSONObject jsonObject = new JSONObject(json);
        Object activeObject = jsonObject.get("active");
        if (activeObject instanceof Integer value) {
            assertTrue(value == 1);
        } else if (activeObject instanceof Boolean value) {
              assertTrue(value);
          }  
    }
    
    @Test
    void givenJSONString_whenParsedWithGoogleJson_correctBooleanValueReturned() {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        boolean active = jsonObject.get("active").getAsBoolean();
        assertTrue(active);
    }
}
