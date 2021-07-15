package com.baeldung.pattern.hexagonal.persistence;

import com.baeldung.pattern.hexagonal.domain.model.Book;

//this one is outbound port 
public interface BookRepository {
	Book getBook(String name);

	String bookEntry(Book book);

}
