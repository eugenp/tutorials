package com.baeldung.pattern.hexagonal.domain.services;

import com.baeldung.pattern.hexagonal.domain.model.Book;

public interface BookService {

	Book getBook(String name);

	String bookEntry(Book book);

}
