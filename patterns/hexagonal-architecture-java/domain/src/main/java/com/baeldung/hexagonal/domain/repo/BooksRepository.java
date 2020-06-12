package com.baeldung.hexagonal.domain.repo;

import com.baeldung.hexagonal.domain.data.Book;

import java.util.UUID;

public interface BooksRepository {

    void save(Book book);

    void delete(UUID id);

}
