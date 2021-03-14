package com.baeldung.hexagonalPattern.ports;

import java.util.List;
import com.baeldung.hexagonalPattern.core.domain.Book;

public interface LibraryService {
//This is the in bound port.exposes the application to the world. 

	public void insertBook(Book book);

	public Book lendBook(String name);

	List<Book> getAllBooks();

}
