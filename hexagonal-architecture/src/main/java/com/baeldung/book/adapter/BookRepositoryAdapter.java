package com.baeldung.book.adapter;

import com.baeldung.book.application.domain.Book;
import com.baeldung.book.application.exception.BookNotFoundException;
import com.baeldung.book.application.port.BookRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BookRepositoryAdapter implements BookRepository {

    private Map<String, Book> books;

    public BookRepositoryAdapter() {
        books = new HashMap<>();

        Book book = new Book("book1");
        book.addPageWithText("This is the first page");

        save(book);
    }

    @Override
    public Book getByName(String name) {
        if (!books.containsKey(name)) {
            throw new BookNotFoundException();
        }

        return books.get(name);
    }

    @Override
    public void save(Book book) {
        books.put(book.getName(), book);
    }
}
