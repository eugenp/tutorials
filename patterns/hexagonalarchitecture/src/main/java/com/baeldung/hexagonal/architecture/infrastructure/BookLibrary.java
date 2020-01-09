package com.baeldung.hexagonal.architecture.infrastructure;

import com.baeldung.hexagonal.architecture.domain.Book;
import com.baeldung.hexagonal.architecture.domain.boundary.BookRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookLibrary implements BookRepository {
    private List<Book> books;

    public BookLibrary() {
        this.books = new ArrayList<>(Arrays.asList(new Book("The Story of Don Juan", "William Shakespeare", "978-3-16-148410-0"), new Book("The Kite Runner", "Khaled Hosseini", "978-3-16-148410-1"),
            new Book("Romeo and Juliet", "William Shakespeare", "978-3-16-148410-2"), new Book("The House of the Spirits", "Isabel Allende", "978-3-16-148410-3")));
    }

    @Override
    public String add(Book book) {
        books.add(book);
        return book.getIsbn();
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return books.stream()
          .filter(book -> book.getIsbn()
            .equals(isbn))
          .findFirst();
    }

    @Override
    public List<Book> findByTitle(String title) {
        return books.stream()
          .filter(book -> book.getAuthor()
            .equals(title))
          .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return books.stream()
          .filter(book -> book.getAuthor()
            .equals(author))
          .collect(Collectors.toList());
    }
}
