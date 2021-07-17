package com.baeldung.pattern.hexagonal.persistence;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baeldung.pattern.hexagonal.domain.model.Book;

@Repository
public class MongoDBBookRepository implements BookRepository {
	@Autowired
	MongoBookRepo mongoBookRepository;

	@Override
	public Optional<Book> getBook(String name) {
		return mongoBookRepository.findById(name);
	}

	@Override
	public Book bookEntry(Book book) {
		return mongoBookRepository.insert(book);
	}

}
