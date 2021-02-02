package com.baeldung.booksapp.core.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.booksapp.core.domain.Book;
import com.baeldung.booksapp.port.BookRepo;
import com.baeldung.booksapp.port.BookService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepo bookRepo;

	@Override
	public void createBook(Book book) {
		bookRepo.createBook(book);
	}

	@Override
	public Book getBook(String name) {
		return bookRepo.getBook(name);

	}

	@Override
	public List<Book> listBook() {
		return bookRepo.getAllBooks();

	}

}
