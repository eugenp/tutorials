package com.baeldung.hexagonal.architecture.application;

import com.baeldung.hexagonal.architecture.domain.Book;
import com.baeldung.hexagonal.architecture.domain.boundary.BookService;

public class Client {
    private BookService bookService;

    public Client(BookService bookService) {
        this.bookService = bookService;
    }

    public String addBook() {
        return bookService.add(new CreateBook("Agatha Christie", "Hercule Poirot", "978-3-16-148410-7").getBook());
    }

    public Book searchForBook() {
        return bookService.searchByIsbn(new SearchForBookByIsbn("978-3-16-148410-7").getIsbn());
    }
}
