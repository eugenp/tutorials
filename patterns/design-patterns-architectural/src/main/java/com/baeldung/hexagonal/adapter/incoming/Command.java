package com.baeldung.hexagonal.adapter.incoming;

import com.baeldung.hexagonal.port.incoming.BookService;

public class Command {
    private BookService bookService;

    public Command(BookService bookService) {
        this.bookService = bookService;
    }

    public void run() {
        bookService.transformBookTitles();
    }
}
