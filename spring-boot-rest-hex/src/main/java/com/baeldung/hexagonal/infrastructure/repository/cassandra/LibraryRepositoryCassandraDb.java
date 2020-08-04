package com.baeldung.hexagonal.infrastructure.repository.cassandra;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.hexagonal.domain.Book;
import com.baeldung.hexagonal.domain.repository.LibraryRepository;

@Component
public class LibraryRepositoryCassandraDb implements LibraryRepository {
    
    private final LibraryRepositorySpringDataCassandra libraryRepository;
    
    @Autowired
    public LibraryRepositoryCassandraDb(
        LibraryRepositorySpringDataCassandra libraryRepository) {
        this.libraryRepository = libraryRepository;
    }
    
    @Override
    public void save(Book book) {
        libraryRepository.save(new BookEntity(book));
    }
    
    @Override
    public Optional<Book> findByTitle(String title) {
        Optional<BookEntity> bookEntity = libraryRepository.findById(title);
        
        if (bookEntity.isPresent()) {
            return Optional.of(bookEntity.get()
                .toBook());
        } else {
            return Optional.empty();
        }
    }
}
