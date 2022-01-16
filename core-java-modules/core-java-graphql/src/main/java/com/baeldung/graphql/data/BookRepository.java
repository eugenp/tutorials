package com.baeldung.graphql.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookRepository {

    private static List<Book> books = null;

    public BookRepository() {
        if (books == null) {
            books = new ArrayList<>();
            books.add(new Book("Title 1", "Author 1"));
            books.add(new Book("Title 2", "Author 2"));
        }
    }

    public List<Book> getAllBooks() {
        return Collections.unmodifiableList(books);
    }

}
