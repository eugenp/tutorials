package com.baeldung.hexagonal.domain.port;

import java.util.List;

import com.baeldung.hexagonal.domain.model.Book;

public interface BookPersistencePort {

	void addBook(Book book);

	void removeBook(Book book);

	List<Book> getBooks();

	Book getBookById(Integer bookId) throws Exception;

}
