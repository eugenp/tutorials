package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.core.domain.Book;
import com.baeldung.hexagonal.port.BookRepo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class BookRepoImpl implements BookRepo {

    private Map<String, Book> bookStore = new HashMap<String, Book>();

    @Override
    public void addBook(Book book) {
        bookStore.put(book.getTitle(),book);
    }

    @Override
    public Book getBook(String title) {
        return bookStore.get(title);
    }
}
