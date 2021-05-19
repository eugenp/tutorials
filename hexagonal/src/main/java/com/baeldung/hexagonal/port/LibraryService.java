package com.baeldung.hexagonal.port;

import com.baeldung.hexagonal.core.domain.Book;

import java.util.List;

public interface LibraryService {

	public void addBook(Book book);

	public Book getBookDetailsByName(String bookName);

	public List<Book> getAllBookDetails();

}
