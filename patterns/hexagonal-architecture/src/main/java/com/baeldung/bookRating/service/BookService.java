package com.baeldung.bookRating.service;

import java.util.List;

import com.baeldung.bookRating.domain.Book;

public interface BookService {
	public List<Book> getBooks();
	
	public Integer getRatingByName(String bookName);
	
	public void addBook(Book book);
}
