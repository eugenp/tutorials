package com.baeldung.hexagonal.repository;

import com.baeldung.hexagonal.domain.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryBookRepositoryImpl implements BookRepository {

    private List<Book> bookStore = new ArrayList<>();

    @Override
    public int addBook(Book newBook) {
        int id = bookStore.size();
        newBook.setId(id);
        bookStore.add(newBook);
        return newBook.getId();
    }

    @Override
    public List<Book> getAllBooks() {
        return bookStore;
    }

    @Override
    public Optional<List<Book>> findBookByName(String name) {

        return Optional.of(bookStore.stream()
            .filter(book -> book.getName().equalsIgnoreCase(name))
            .collect(Collectors.toList()));
    }
}
