package com.baeldung.hexagonal.domain.repository;

import java.util.Optional;

import com.baeldung.hexagonal.domain.Book;

public interface LibraryRepository {
	void save(Book book);
	
	Optional<Book> findByTitle(String title);
}
