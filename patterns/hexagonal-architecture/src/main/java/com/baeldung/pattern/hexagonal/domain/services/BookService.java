package com.baeldung.pattern.hexagonal.domain.services;

import com.baeldung.pattern.hexagonal.domain.model.Book;

//this one is inbound port

public interface BookService {

	Book getBook(String name);

	String bookEntry(Book book);

}
