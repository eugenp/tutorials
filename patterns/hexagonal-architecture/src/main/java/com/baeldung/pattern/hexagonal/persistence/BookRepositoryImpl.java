package com.baeldung.pattern.hexagonal.persistence;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.baeldung.pattern.hexagonal.domain.model.Book;

@Repository
public class BookRepositoryImpl implements BookRepository {

	private Map<String, Book> bookMap = new HashMap<>();

	@Override
	public Book getBook(String name) {

		return bookMap.get(name);
	}

	@Override
	public String bookEntry(Book book) {
		bookMap.put(book.getName(), book);
		return book.getName();
	}

}
