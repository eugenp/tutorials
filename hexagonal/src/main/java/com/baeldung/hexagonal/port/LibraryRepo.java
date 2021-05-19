package com.baeldung.hexagonal.port;

import com.baeldung.hexagonal.core.domain.Book;

import java.util.List;

public interface LibraryRepo {

	void addBook(Book book);

	Book getBookDetailsByName(String bookName);

	List<Book> getAllBookDetails();
}
