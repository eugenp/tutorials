package com.baeldung.spqr;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class BookService implements IBookService {

    private static final Set<Book> BOOKS_DATA = initializeData();

    @Override
    public Book getBookWithTitle(String title) {
        return BOOKS_DATA.stream()
            .filter(book -> book.getTitle().equals(title))
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(BOOKS_DATA);
    }

    @Override
    public Book addBook(Book book) {
        BOOKS_DATA.add(book);
        return book;
    }

    @Override
    public Book updateBook(Book book) {
        BOOKS_DATA.removeIf(b -> Objects.equals(b.getId(), book.getId()));
        BOOKS_DATA.add(book);
        return book;
    }

    @Override
    public boolean deleteBook(Book book) {
        return BOOKS_DATA.remove(book);
    }

    private static Set<Book> initializeData() {
        Book book = new Book(1, "J.R.R. Tolkien", "The Lord of the Rings");
        Set<Book> books = new HashSet<>();
        books.add(book);
        return books;
    }
}
