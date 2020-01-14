package com.baeldung.bookRating.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.bookRating.domain.Book;
import com.baeldung.bookRating.port.BookRepoOutbound;

@Service
public class BookServiceImpl implements BookService{
	
	@Autowired
	private BookRepoOutbound bookRepo;

	@Override
	public List<Book> getBooks() {
		return bookRepo.getBooks();
	}

	@Override
	public Integer getRatingByName(String bookName) {
		return bookRepo.getRatingByName(bookName);
	}

	@Override
	public void addBook(Book book) {
		bookRepo.addBook(book);
	}

}
