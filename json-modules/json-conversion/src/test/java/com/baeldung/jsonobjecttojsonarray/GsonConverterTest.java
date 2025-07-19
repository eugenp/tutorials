package com.baeldung.jsonobjecttojsonarray;

import com.google.gson.JsonArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GsonConverterTest {

    @Test
    void givenJSONObject_whenConvertToKeyValueArray_thenJsonArrayWithObjects() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("brand", "Tesla");
        jsonObject.put("year", 2024);

        GsonConverter converter = new GsonConverter();
        System.out.println("before :"+jsonObject);
        JsonArray result = converter.convertToKeyValueArray(jsonObject);

        System.out.println("here :"+result);

        assertEquals(2, result.size());
        assertEquals("year", result.get(0).getAsJsonObject().get("key").getAsString());
    }
}
