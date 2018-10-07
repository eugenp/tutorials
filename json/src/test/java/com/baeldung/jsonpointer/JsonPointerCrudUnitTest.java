package com.baeldung.jsonpointer;

import static org.junit.Assert.*;

import org.junit.Test;

public class JsonPointerCrudUnitTest {

    @Test
    public void testJsonPointerCrudForAddress() {

        JsonPointerCrud jsonPointerCrud = new JsonPointerCrud(JsonPointerCrudUnitTest.class.getResourceAsStream("/address.json"));

        assertFalse(jsonPointerCrud.check("city"));

        // insert a value
        jsonPointerCrud.insert("city", "Rio de Janeiro");

        assertTrue(jsonPointerCrud.check("city"));

        // fetch full json
        String fullJSON = jsonPointerCrud.fetchFullJSON();

        assertTrue(fullJSON.contains("name"));

        assertTrue(fullJSON.contains("city"));

        // fetch value
        String cityName = jsonPointerCrud.fetchValueFromKey("city");

        assertEquals(cityName, "Rio de Janeiro");

        // update value
        jsonPointerCrud.update("city", "Sao Paulo");

        // fetch value
        cityName = jsonPointerCrud.fetchValueFromKey("city");

        assertEquals(cityName, "Sao Paulo");

        // delete
        jsonPointerCrud.delete("city");

        assertFalse(jsonPointerCrud.check("city"));

    }

    @Test
    public void testJsonPointerCrudForBooks() {

        JsonPointerCrud jsonPointerCrud = new JsonPointerCrud(JsonPointerCrudUnitTest.class.getResourceAsStream("/books.json"));

        // fetch value
        String book = jsonPointerCrud.fetchListValues("books/1");

        assertEquals(book, "{\"title\":\"Title 2\",\"author\":\"John Doe\"}");

    }
}