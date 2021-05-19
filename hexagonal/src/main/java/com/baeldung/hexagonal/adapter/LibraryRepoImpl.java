package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.core.domain.Book;
import com.baeldung.hexagonal.port.LibraryRepo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LibraryRepoImpl implements LibraryRepo {

	private Map<String, Book> library = new HashMap<String, Book>();

	@Override
	public void addBook(Book book) {
		library.put(book.getName(), book);
	}

	@Override
	public Book getBookDetailsByName(String name) {
		return library.get(name);
	}

	@Override
	public List<Book> getAllBookDetails() {
		return new ArrayList<>(library.values());
	}

}