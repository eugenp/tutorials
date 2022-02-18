package com.baeldung.pattern.architecture.hexagonal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TestHelper {
    private TestHelper() {
    }

    public static List<String> mockDataFromFile() {
        return Arrays.asList("1;The Jungle Book;Rudyard Kipling", "2;Kane and Abel;Jeffrey Archer");
    }

    public static List<JsonObject> mockDataFromMongoDb() {
        String bookDataJson = "[{\"id\":\"1\", \"bookName\":\"The Jungle Book\", \"authorName\":\"Rudyard Kipling\"},{\"id\":\"2\", \"bookName\":\"Kane and Abel\", \"authorName\":\"Jeffrey Archer\"}]";
        JsonArray bookArray = (JsonArray) JsonParser.parseString(bookDataJson);
        List<JsonObject> books = new ArrayList<>();
        for (int i = 0; i < bookArray.size(); i++) {
            books.add(bookArray.get(i)
                .getAsJsonObject());
        }
        return books;
    }
}
