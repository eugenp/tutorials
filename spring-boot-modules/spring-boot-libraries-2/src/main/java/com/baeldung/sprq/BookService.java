package com.baeldung.sprq;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService implements IBookService {

    Set<Book> books = new HashSet<>();

    @Override
    public Book getBookWithTitle(String title) {
        return books.stream()
            .filter(book -> book.getTitle()
                .equals(title))
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<Book> getAllBooks() {
        return books.stream()
            .collect(Collectors.toList());
    }

    @Override
    public Book addBook(Book book) {
        books.add(book);
        return book;
    }

    @Override
    public Book updateBook(Book book) {
        books.remove(book);
        books.add(book);
        return book;
    }

    @Override
    public boolean deleteBook(Book book) {
        return books.remove(book);
    }
}
