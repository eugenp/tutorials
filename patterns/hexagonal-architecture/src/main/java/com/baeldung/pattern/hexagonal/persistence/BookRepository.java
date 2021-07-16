package com.baeldung.pattern.hexagonal.persistence;

import com.baeldung.pattern.hexagonal.domain.model.Book;

public interface BookRepository {
	Book getBook(String name);

	String bookEntry(Book book);

}
