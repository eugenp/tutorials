package com.baeldung.hexagonalPattern.ports;

import java.util.List;
import com.baeldung.hexagonalPattern.core.domain.Book;

public interface LibraryRepo {
	// Outbound Port.

	public void insertBook(Book book);

	public Book searchBook(String name);

	public List<Book> getAllBooks();
}
