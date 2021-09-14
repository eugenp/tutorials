package com.baeldung.boot.hexagonal.domain.ports.service;

import com.baeldung.boot.hexagonal.domain.models.Book;
import com.baeldung.boot.hexagonal.domain.ports.in.BookService;
import com.baeldung.boot.hexagonal.domain.ports.out.BookPersistence;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookPersistence bookPersistence;

    /**
     * Adds a book into repository
     * @param book The data transfer object
     */
    @Override
    public void addBook(Book book) {
        bookPersistence.addBook(book);
    }

    /**
     * Update a book in repository
     * @param book The data transfer object
     */
    @Override
    public void updateBook(Book book) {
        bookPersistence.updateBook(book);
    }

    /**
     * Returns a Book object for a given book identifier
     * @param id The identifier for which the book has to be retrieved
     * @return The book
     * @see Book
     */
    @Override
    public Book getBook(long id) {
        return bookPersistence.getBook(id);
    }

    /**
     * Returns all the books in the library.
     * @return Returns a list of book
     * @see Book
     */
    @Override
    public List<Book> getBooks() {
        return bookPersistence.getBooks();
    }

    /**
     * Delete a book with a given id
     * @param id The book id that has to be deleted
     */
    @Override
    public void deleteBook(long id) {
        bookPersistence.deleteBook(id);
    }
}
