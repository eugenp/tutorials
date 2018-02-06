package com.baeldung.microprofile.util;

import com.baeldung.microprofile.model.Book;

import javax.json.*;
import java.util.List;

public class Mapper {

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
}
