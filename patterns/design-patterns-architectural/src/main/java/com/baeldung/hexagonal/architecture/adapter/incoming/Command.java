package com.baeldung.hexagonal.architecture.adapter.incoming;

import com.baeldung.hexagonal.architecture.port.incoming.BookService;

public class Command {
    private BookService bookService;

    public Command(BookService bookService) {
        this.bookService = bookService;
    }

    public void run() {
        bookService.invoke();
    }
}
