package com.baeldung.microprofile.util;

import com.baeldung.microprofile.model.Book;

import javax.json.Json;
import javax.json.JsonObject;

public class Utils {

    public static Book map(JsonObject jsonbook) {
        Book book = new Book();
        book.setId(jsonbook.getString("id",""));
        book.setName(jsonbook.getString("name",""));
        book.setIsbn(jsonbook.getString("isbn",""));
        book.setAuthor(jsonbook.getString("author",""));
        book.setNbrePages(jsonbook.getInt("nbrePages",0));
        return book;
    }

    public static JsonObject map(Book book) {
        return Json.createObjectBuilder()
                .add("id", book.getId())
                .add("name", book.getName())
                .add("author", book.getAuthor())
                .add("nbrePages", book.getNbrePages())
                .add("isbn", book.getIsbn())
                .build();
    }

}
