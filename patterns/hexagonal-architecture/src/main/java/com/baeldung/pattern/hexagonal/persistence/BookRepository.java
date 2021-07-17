package com.baeldung.pattern.hexagonal.persistence;

import java.util.Optional;

import com.baeldung.pattern.hexagonal.domain.model.Book;

public interface BookRepository {
	Optional<Book> getBook(String name);

	Book bookEntry(Book book);

}
