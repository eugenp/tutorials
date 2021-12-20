package com.baeldung.hexagonalarchitecture.infrastructure;

import java.util.List;
import java.util.stream.Collectors;

import com.baeldung.hexagonalarchitecture.domain.Book;
import com.baeldung.hexagonalarchitecture.domain.IBookRepository;

public class DatabaseBookRepository implements IBookRepository {

    private JpaBookRepository jpaBookRepository;

    public DatabaseBookRepository(JpaBookRepository jpaBookRepository) {
        this.jpaBookRepository = jpaBookRepository;
    }

    @Override
    public List<Book> findAllBooks() {
        return jpaBookRepository.findAll()
            .stream()
            .map(bookEntity -> new Book(bookEntity.getIsbn(), bookEntity.getTitle()))
            .collect(Collectors.toList());
    }
}
