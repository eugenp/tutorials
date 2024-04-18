package com.baeldung.injectmockintospy;

import java.util.LinkedList;
import java.util.List;

import lombok.Getter;

@Getter
public class BookStorageService {

    private BookControlService bookControlService;
    private final List<Book> availableBooks = new LinkedList<>();

    public BookStorageService(BookControlService bookControlService) {
        this.bookControlService = bookControlService;
    }

    public void returnBook(Book book) {
        availableBooks.add(book);
        bookControlService.returnBook(book);
    }

    public void giveBook(Book book) {
        availableBooks.remove(book);
        bookControlService.giveBook(book);
    }

}
