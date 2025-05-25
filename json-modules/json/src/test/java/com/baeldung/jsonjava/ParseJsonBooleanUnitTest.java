package com.baeldung.jsonjava;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.json.JSONObject;


public class ParseJsonBooleanUnitTest {


    @Test
    void givenJSONString_whenParsed_correctBooleanValueReturned() {
        String jsonString = "{\"name\":\"lorem ipsum\",\"active\":true,\"id\":1}";
        JSONObject jsonObject = new JSONObject(jsonString);
        boolean active = jsonObject.getBoolean("active");
        assertTrue(active);
    }

     
}
