package com.baeldung.hexagonal.library.repository;

import com.baeldung.hexagonal.library.domain.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository {

    List<Book> findAll();
}
