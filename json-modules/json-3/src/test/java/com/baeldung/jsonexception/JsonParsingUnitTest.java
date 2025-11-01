package com.baeldung.jsonexception;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonParsingUnitTest {

    @Test
    void givenArrayString_whenParsedAsObject_thenThrowException() {
        String jsonArray = "[{\"id\":1, \"name\":\"Alice\"}]";
        assertThrows(JSONException.class, () -> new JSONObject(jsonArray));
    }

    @Test
    void givenArrayString_whenParsedAsArray_thenSuccess() {
        String jsonArray = "[{\"id\":1, \"name\":\"Alice\"}]";
        JSONArray array = new JSONArray(jsonArray);
        assertEquals("Alice", array.getJSONObject(0).getString("name"));
    }

    @Test
    void givenInvalidJson_whenParsed_thenThrowException() {
        String invalid = "<html>Server Error</html>";
        assertThrows(JSONException.class, () -> new JSONObject(invalid));
    }

    @Test
    void givenEmptyString_whenParsed_thenThrowException() {
        String empty = "";
        assertThrows(JSONException.class, () -> new JSONObject(empty));
    }

    @Test
    void givenValidJson_whenParsed_thenReturnExpectedValue() {
        String json = "{\"id\":101, \"status\":\"success\"}";
        JSONObject obj = new JSONObject(json);
        assertEquals(101, obj.getInt("id"));
        assertEquals("success", obj.getString("status"));
    }

    @Test
    void givenUnknownJsonType_whenValidated_thenHandledGracefully() {
        String response = "[{\"id\":1}]";
        Object parsed;
        if (response.trim().startsWith("{")) {
            parsed = new JSONObject(response);
        } else if (response.trim().startsWith("[")) {
            parsed = new JSONArray(response);
        } else {
            throw new JSONException("Invalid JSON");
        }
        assertInstanceOf(JSONArray.class, parsed);
    }
}

