package com.baeldung.hexagonal.infrastructure.repository.mongo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Primary;

import com.baeldung.hexagonal.domain.Book;
import com.baeldung.hexagonal.domain.repository.LibraryRepository;

@Component
@Primary
public class LibraryRepositoryMongoDb implements LibraryRepository {
	
	private final LibraryRepositorySpringDataMongoDb libraryRepository;
	
	@Autowired
	public LibraryRepositoryMongoDb(
			LibraryRepositorySpringDataMongoDb libraryRepository) {
		this.libraryRepository = libraryRepository;	
	}
	
	@Override
	public void save(final Book book) {
		libraryRepository.save(book);
	}
	
	@Override
	public Optional<Book> findByTitle(String title) {
		return libraryRepository.findById(title);	
	}
}
