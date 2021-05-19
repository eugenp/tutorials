package com.baeldung.hexagonal.core.impl;

import com.baeldung.hexagonal.core.domain.Book;
import com.baeldung.hexagonal.port.LibraryRepo;
import com.baeldung.hexagonal.port.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService {

	@Autowired
	private LibraryRepo libraryRepo;

	@Override
	public void addBook(Book book) {
		libraryRepo.addBook(book);
	}

	@Override
	public Book getBookDetailsByName(String name) {
		return libraryRepo.getBookDetailsByName(name);
	}

	@Override
	public List<Book> getAllBookDetails() {
		return libraryRepo.getAllBookDetails();
	}

}