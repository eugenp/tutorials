package com.baeldung.boot.hexagonal.domain.ports.in;

import com.baeldung.boot.hexagonal.domain.models.Book;

import java.util.List;

public interface BookService {

    /**
     * Adds a book into repository
     * @param book The data transfer object
     */
    void addBook(Book book);

    /**
     * Update a book into repository
     * @param book The data transfer object
     */
    void updateBook(Book book);

    /**
     * Returns a Book object for a given book identifier
     * @param id The identifier for which the book has to be retrieved
     * @return The book
     * @see Book
     */
    Book getBook(long id);

    /**
     * Returns all the books in the library.
     * @return Returns a list of book
     * @see Book
     */
    List<Book> getBooks();

    /**
     * Delete a book with a given id
     * @param id The book id that has to be deleted
     */
    void deleteBook(long id);

}
