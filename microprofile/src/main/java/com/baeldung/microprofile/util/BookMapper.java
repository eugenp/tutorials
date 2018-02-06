package com.baeldung.microprofile.util;

import com.baeldung.microprofile.model.Book;

import javax.json.*;
import java.io.InputStream;
import java.util.List;

public class BookMapper {

    public static JsonObject map(Book book) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder = builder
                .add("id", book.getId())
                .add("isbn", book.getIsbn())
                .add("isbn22", book.getIsbn())
                .add("name", book.getName())
                .add("author", book.getAuthor())
                .add("pages", book.getPages());
        return builder.build();
    }

    public static JsonArray map(List<Book> books) {
        final JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        books.forEach(book -> {
            JsonObject jsonObject = map(book);
            arrayBuilder.add(jsonObject);
        });
        return arrayBuilder.build();
    }

    public static Book map(InputStream is) {
        try(JsonReader jsonReader = Json.createReader(is)) {
            JsonObject jsonObject = jsonReader.readObject();
            Book book = new Book();
            book.setId(getStringFromJson("id", jsonObject));
            book.setIsbn(getStringFromJson("isbn", jsonObject));
            book.setName(getStringFromJson("name", jsonObject));
            book.setAuthor(getStringFromJson("author", jsonObject));
            book.setPages(getIntFromJson("pages",jsonObject));
            return book;
        }
    }

    private static String getStringFromJson(String key, JsonObject json) {
        String returnedString = null;
        if (json.containsKey(key)) {
            JsonString value = json.getJsonString(key);
            if (value != null) {
                returnedString = value.getString();
            }
        }
        return returnedString;
    }

    private static int getIntFromJson(String key, JsonObject json) {
        int returnedValue = 0;
        if (json.containsKey(key)) {
            JsonNumber value = json.getJsonNumber(key);
            if (value != null) {
                returnedValue = value.intValue();
            }
        }
        return returnedValue;
    }
}
