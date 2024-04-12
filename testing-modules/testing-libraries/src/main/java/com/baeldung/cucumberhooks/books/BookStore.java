package com.baeldung.cucumberhooks.books;

import java.util.*;
import java.util.stream.Collectors;

public class BookStore {
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public void addAllBooks(Collection<Book> books) {
        this.books.addAll(books);
    }

    public List<Book> booksByAuthor(String author) {
        return books.stream()
          .filter(book -> Objects.equals(author, book.getAuthor()))
          .collect(Collectors.toList());
    }

    public Optional<Book> bookByTitle(String title) {
        return books.stream()
          .filter(book -> book.getTitle().equals(title))
          .findFirst();
    }
}
