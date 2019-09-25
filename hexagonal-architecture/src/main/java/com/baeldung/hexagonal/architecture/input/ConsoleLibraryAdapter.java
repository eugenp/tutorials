package com.baeldung.hexagonal.architecture.input;

import com.baeldung.hexagonal.architecture.core.LibraryInterface;
import com.baeldung.hexagonal.architecture.model.Book;

import java.io.PrintStream;

public class ConsoleLibraryAdapter {
    private final LibraryInterface library;
    private final PrintStream out;

    public ConsoleLibraryAdapter(LibraryInterface library, PrintStream out) {
        this.library = library;
        this.out = out;
    }

    public void showBooks() {
        library.getBooks().forEach(this::printBook);
    }

    private void printBook(Book book) {
        out.println(book.getId() + "> " + book.getTitle());
    }

    public void rentBook(long bookId, long userId) {
        try {
            out.println("Requesting to rent book: " + bookId);
            library.rentBook(bookId, userId);
            out.println("Rented book!");
        } catch (IllegalStateException | IllegalArgumentException e) {
            out.println("Failed to rent: " + e.getMessage());
        }
    }
}
