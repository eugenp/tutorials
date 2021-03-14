package com.baeldung.hexagonalPattern.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.baeldung.hexagonalPattern.core.domain.Book;
import com.baeldung.hexagonalPattern.ports.LibraryRepo;

@Repository
public class LibraryRepoImpl implements LibraryRepo {
	// This class is the actual implementation of the out bound port/adapter.

	private HashMap<String, Book> books = new HashMap<String, Book>();

	@Override
	public void insertBook(Book book) {
		// Mock Database call here.
		books.put("mock", new Book("mock", "mock", "mock"));
	}

	@Override
	public Book searchBook(String name) {
		// TODO Auto-generated method stub
		Book b = new Book();
		// Some code for retrieval of book from db
		return b;
	}

	@Override
	public List<Book> getAllBooks() {
		// Fetch all books from db
		return books.values().stream().collect(Collectors.toList());
	}

}
