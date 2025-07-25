package com.baeldung.jsonobjecttojsonarray;

import org.json.JSONObject;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JacksonConverterTest {

    @Test
    void givenJSONObject_whenConvertToArray_thenArrayNodeOfKeyValueObjects() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("country", "India");
        jsonObject.put("code", "IN");

        JacksonConverter converter = new JacksonConverter();
        ArrayNode result = converter.convertToArray(jsonObject);

        assertEquals(2, result.size());
        assertEquals("country", result.get(0).get("key").asText());
    }
}
