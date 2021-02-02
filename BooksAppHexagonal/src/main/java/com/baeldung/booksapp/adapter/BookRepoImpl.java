package com.baeldung.booksapp.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.baeldung.booksapp.core.domain.Book;
import com.baeldung.booksapp.port.BookRepo;

@Repository
public class BookRepoImpl implements BookRepo {

	private Map<String, Book> library = new HashMap<String, Book>();

	@Override
	public void createBook(Book book) {
		library.put(book.getName(), book);
		
	}

	@Override
	public Book getBook(String name) {
 		return library.get(name);

	}

	@Override
	public List<Book> getAllBooks() {
		return library.values().stream().collect(Collectors.toList());
	}

}
