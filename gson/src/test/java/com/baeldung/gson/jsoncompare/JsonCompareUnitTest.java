package com.baeldung.gson.jsoncompare;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.Test;
import static org.junit.Assert.*;

public class JsonCompareUnitTest {
    @Test
    public void givenJsonStrings_whenCompared_thenNotEqual() {
        String string1 = "{\"fullName\": \"Emily Jenkins\", \"age\": 27    }";
        String string2 = "{\"fullName\": \"Emily Jenkins\", \"age\": 27}";

        assertNotEquals(string1, string2);
    }

    @Test
    public void givenIdenticalSimpleObjects_whenCompared_thenEqual() {
        JsonParser parser = new JsonParser();
        String string1 = "{\"customer\": {\"id\": \"44521\",\"fullName\": \"Emily Jenkins\", \"age\": 27 }}";
        String string2 = "{\"customer\": {\"id\": \"44521\", \"fullName\": \"Emily Jenkins\",\"age\": 27}}";

        assertTrue(parser.parse(string1)
            .isJsonObject());
        assertEquals(parser.parse(string1), parser.parse(string2));
    }

    @Test
    public void givenSameObjectsInDifferentOrder_whenCompared_thenEqual() {
        JsonParser parser = new JsonParser();
        String string1 = "{\"customer\": {\"id\": \"44521\",\"fullName\": \"Emily Jenkins\", \"age\": 27 }}";
        String string2 = "{\"customer\": {\"id\": \"44521\",\"age\": 27, \"fullName\": \"Emily Jenkins\" }}";

        JsonElement json1 = parser.parse(string1);
        JsonElement json2 = parser.parse(string2);

        assertEquals(json1, json2);
    }

    @Test
    public void givenIdenticalArrays_whenCompared_thenEqual() {
        JsonParser parser = new JsonParser();
        String string1 = "[10, 20, 30]";
        String string2 = "[10, 20, 30]";

        assertTrue(parser.parse(string1)
            .isJsonArray());
        assertEquals(parser.parse(string1), parser.parse(string2));
    }

    @Test
    public void givenArraysInDifferentOrder_whenCompared_thenNotEqual() {
        JsonParser parser = new JsonParser();
        String string1 = "[20, 10, 30]";
        String string2 = "[10, 20, 30]";

        assertNotEquals(parser.parse(string1), parser.parse(string2));
    }

    @Test
    public void givenIdenticalNestedObjects_whenCompared_thenEqual() {
        JsonParser parser = new JsonParser();
        String string1 = "{\"customer\": {\"id\": \"44521\",\"fullName\": \"Emily Jenkins\", \"age\": 27, \"consumption_info\" : {\"fav_product\": \"Coke\", \"last_buy\": \"2012-04-23\"}}}";
        String string2 = "{\"customer\": {\"id\": \"44521\",\"fullName\": \"Emily Jenkins\", \"age\": 27, \"consumption_info\" : {\"last_buy\": \"2012-04-23\", \"fav_product\": \"Coke\"}}}";

        JsonElement json1 = parser.parse(string1);
        JsonElement json2 = parser.parse(string2);

        assertEquals(json1, json2);
    }

    @Test
    public void givenIdenticalNestedObjectsWithArray_whenCompared_thenEqual() {
        JsonParser parser = new JsonParser();
        String string1 = "{\"customer\": {\"id\": \"44521\",\"fullName\": \"Emily Jenkins\", \"age\": 27, \"consumption_info\" : {\"last_buy\": \"2012-04-23\", \"prouducts\": [\"banana\", \"eggs\"]}}}";
        String string2 = "{\"customer\": {\"id\": \"44521\",\"fullName\": \"Emily Jenkins\", \"age\": 27, \"consumption_info\" : {\"last_buy\": \"2012-04-23\", \"prouducts\": [\"banana\", \"eggs\"]}}}";

        JsonElement json1 = parser.parse(string1);
        JsonElement json2 = parser.parse(string2);

        assertEquals(json1, json2);
    }

    @Test
    public void givenNestedObjectsDifferentArrayOrder_whenCompared_thenNotEqual() {
        JsonParser parser = new JsonParser();
        String string1 = "{\"customer\": {\"id\": \"44521\",\"fullName\": \"Emily Jenkins\", \"age\": 27, \"consumption_info\" : {\"last_buy\": \"2012-04-23\", \"prouducts\": [\"banana\", \"eggs\"]}}}";
        String string2 = "{\"customer\": {\"id\": \"44521\",\"fullName\": \"Emily Jenkins\", \"age\": 27, \"consumption_info\" : {\"last_buy\": \"2012-04-23\", \"prouducts\": [\"eggs\", \"banana\"]}}}";

        JsonElement json1 = parser.parse(string1);
        JsonElement json2 = parser.parse(string2);

        assertNotEquals(json1, json2);
    }
}
