package com.baeldung.microprofile.util;

import com.baeldung.microprofile.model.Book;
import jakarta.json.*;

import java.io.InputStream;
import java.util.List;

public class BookMapper {

    public static JsonObject map(Book book) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        addValue(builder, "id", book.getId());
        addValue(builder, "isbn", book.getIsbn());
        addValue(builder, "name", book.getName());
        addValue(builder, "author", book.getAuthor());
        addValue(builder, "pages", book.getPages());
        return builder.build();
    }

    private static void addValue(JsonObjectBuilder builder, String key, Object value) {
        if (value != null) {
            builder.add(key, value.toString());
        } else {
            builder.addNull(key);
        }
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

    private static Integer getIntFromJson(String key, JsonObject json) {
        Integer returnedValue = null;
        if (json.containsKey(key)) {
            JsonNumber value = json.getJsonNumber(key);
            if (value != null) {
                returnedValue = value.intValue();
            }
        }
        return returnedValue;
    }
}
