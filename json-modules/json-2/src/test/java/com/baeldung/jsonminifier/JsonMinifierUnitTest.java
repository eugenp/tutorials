package com.baeldung.jsonminifier;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

public class JsonMinifierUnitTest {
    private JsonMinifier jsonMinifier = new JsonMinifier();
    private String inputJson = "{      \"name\" : \"John\"  , \"address\" :   \"New       York\", \"age\" : 30   , \"phoneNumber\" : 9999999999    }";


    @Test
    public void givenWhiteSpaceRemoval_whenJsonContainsWhitespaces_thenWhitespaceRemoved() {
        String expectedJson = "{\"name\":\"John\",\"address\":\"New       York\",\"age\":30,\"phoneNumber\":9999999999}";
        String result = jsonMinifier.removeExtraWhitespace(inputJson);
        System.out.println(result);
        assertEquals(expectedJson, result);
    }

    @Test
    public void givenWhiteSpaceRemovalUsingJackson_whenJsonContainsWhitespaces_thenWhitespaceRemoved() throws Exception {
        String expectedJson = "{\"name\":\"John\",\"address\":\"New       York\",\"age\":30,\"phoneNumber\":9999999999}";
        String result = jsonMinifier.removeExtraWhitespaceUsingJackson(inputJson);
        System.out.println(result);
        assertEquals(expectedJson, result);
    }

    @Test
    public void givenWhiteSpaceRemovalUsingGson_whenJsonContainsWhitespaces_thenWhitespaceRemoved() {
        String expectedJson = "{\"name\":\"John\",\"address\":\"New       York\",\"age\":30,\"phoneNumber\":9999999999}";
        String result = jsonMinifier.removeWhitespacesUsingGson(inputJson);
        System.out.println(result);
        assertEquals(expectedJson, result);
    }
}
