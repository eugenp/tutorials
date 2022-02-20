package com.baeldung.graphql.data;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BookRepository {

    private static final List<Book> books = Stream.of(
            new Book("Title 1", new Author("Pero", "Peric")),
            new Book("Title 2", new Author("Marko", "Maric"))
        ).collect(Collectors.toList());

    public List<Book> getAllBooks() {
        return Collections.unmodifiableList(books);
    }

}
