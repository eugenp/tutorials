package com.baeldung.hexagoninjava.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baeldung.hexagoninjava.core.domain.Book;
import com.baeldung.hexagoninjava.port.BookRepo;
import org.springframework.stereotype.Repository;


@Repository
public class BookRepoImpl implements BookRepo {

    private Map<String, Book> bookStore = new HashMap<String, Book>();

    @Override
    public void createBook(Book book) {
        bookStore.put(book.getName(), book);
    }

    @Override
    public Book getBook(String name) {
        return bookStore.get(name);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookStore.values().stream().collect(Collectors.toList());
    }

}