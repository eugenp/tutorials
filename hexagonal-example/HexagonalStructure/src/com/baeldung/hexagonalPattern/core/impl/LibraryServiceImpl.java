package com.baeldung.hexagonalPattern.core.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baeldung.hexagonalPattern.core.domain.Book;
import com.baeldung.hexagonalPattern.ports.LibraryRepo;
import com.baeldung.hexagonalPattern.ports.LibraryService;

@Service
public class LibraryServiceImpl implements LibraryService {

	// This is the class which actually implements the methods from the ports.
	// The ports are just for exposing the methods to the outside.

	@Autowired
	private LibraryRepo bookRepo;

	@Override
	public void insertBook(Book book) {
		// TODO some implementation to insert record in the db or similar
		bookRepo.insertBook(book);
	}

	@Override
	public Book lendBook(String name) {
		// TODO Auto-generated method stub
		return bookRepo.searchBook(name);
	}

	@Override
	public List<Book> getAllBooks() {
		// TODO Auto-generated method
		return null;
	}

}
