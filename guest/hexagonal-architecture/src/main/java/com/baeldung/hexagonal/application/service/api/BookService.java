package com.baeldung.hexagonal.application.service.api;

import java.util.List;

import com.baeldung.hexagonal.domain.model.Book;

public interface BookService {

	void addBook(Book book);

	void removeBook(Book book);

	List<Book> getBooks();

	Book getBookById(Integer bookId) throws Exception;
}
