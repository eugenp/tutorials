package com.baeldung.hexagonal;

import java.util.List;
import java.util.stream.Collectors;

class MockLoadBooksListImpl implements LoadBooksList {

    @Override
    public List<Book> loadBooksList() {
        List<String> names = List.of("In Search of Lost Time", "Ulysses", "Don Quixote", "One Hundred Years of Solitude", "The Great Gatsby");

        List<Book> books = names.stream()
            .map(name -> new Book(Double.valueOf(Math.random())
                .longValue(), name))
            .collect(Collectors.toList());

        return books;
    }
}