package com.baeldung.hexagonal.architecture.output;

import com.baeldung.hexagonal.architecture.domain.Book;

import java.util.List;

// contains all the data methods
// outbound port
public interface BookRepository {

    public void saveBook(Book book);

    public void updateBook(Book book);

    public void deleteBook(String id);

    public Book getBookById(String id);

    public List<Book> getAllBooks();
}
