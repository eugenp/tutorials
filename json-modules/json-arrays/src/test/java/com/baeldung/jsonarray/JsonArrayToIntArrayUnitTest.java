package com.baeldung.jsonarray;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;


class JsonArrayToIntArrayUnitTest {

    @Test
    void givenJsonArray_whenUsingLoop_thenIntArrayIsReturned() {
        JSONArray jsonArray = new JSONArray("[1, 2, 3]");
        int[] result = new int[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            result[i] = jsonArray.getInt(i);
        }

        assertArrayEquals(new int[]{1, 2, 3}, result);
    }

    @Test
    void givenJsonArray_whenUsingStreams_thenIntArrayIsReturned() {
        JSONArray jsonArray = new JSONArray("[10, 20, 30]");
        int[] result = IntStream.range(0, jsonArray.length())
                .map(jsonArray::getInt)
                .toArray();

        assertArrayEquals(new int[]{10, 20, 30}, result);
    }

    @Test
    void givenNullJsonArray_whenConvertingSafely_thenEmptyArrayIsReturned() {
        int[] result = toIntArraySafely(null);
        assertEquals(0, result.length);
    }

    @Test
    void givenEmptyJsonArray_whenConvertingSafely_thenEmptyArrayIsReturned() {
        JSONArray jsonArray = new JSONArray();
        int[] result = toIntArraySafely(jsonArray);
        assertEquals(0, result.length);
    }

    private int[] toIntArraySafely(JSONArray jsonArray) {
        if (jsonArray == null || jsonArray.isEmpty()) {
            return new int[0];
        }

        int[] result = new int[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            result[i] = jsonArray.getInt(i);
        }
        return result;
    }
}
