package com.baeldung.hexagonal.architecture.adapter.in;

import com.baeldung.hexagonal.architecture.port.in.BookService;

// adapter
// Can simulate instead
public class Command {
    private BookService bookService;

    public Command(BookService bookService) {
        this.bookService = bookService;
    }

    public void run() {
        bookService.getBooks();
    }
}
