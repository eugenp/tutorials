package com.baeldung.hexagonal.architecture.core;

import com.baeldung.hexagonal.architecture.model.Book;

import java.util.List;

public interface LibraryInterface {
    List<Book> getBooks();

    /**
     * Rent a book from the library
     *
     * @throws IllegalArgumentException when the book is not available for rent
     */
    void rentBook(long bookId, long userId);
}
