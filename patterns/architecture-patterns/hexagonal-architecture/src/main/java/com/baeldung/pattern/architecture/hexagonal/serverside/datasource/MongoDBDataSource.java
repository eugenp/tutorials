package com.baeldung.pattern.architecture.hexagonal.serverside.datasource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.baeldung.pattern.architecture.hexagonal.domain.entity.Book;
import com.baeldung.pattern.architecture.hexagonal.domain.repository.BookRepository;
import com.google.gson.JsonObject;

public class MongoDBDataSource implements BookRepository {

    @Override
    public List<Book> getBooks() {
        return getAllBookDocuments().stream()
            .map(this::mapBookJsonObjectToBook)
            .collect(Collectors.toList());
    }

    public List<JsonObject> getAllBookDocuments() {
        // code for reading book data from MongoDB
        return new ArrayList<>();
    }

    private Book mapBookJsonObjectToBook(JsonObject bookJson) {
        return new Book(Integer.parseInt(bookJson.get("id")
            .getAsString()), bookJson.get("bookName")
            .getAsString(), bookJson.get("authorName")
            .getAsString());
    }

}
