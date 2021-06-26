package com.baeldung.hexagon.architecture.service;

import java.util.List;

import com.baeldung.hexagon.architecture.domain.entity.Book;

public interface BookService {

	void addBook(Book book);

	Book getBook(String title);

	List<Book> getBooks();
}