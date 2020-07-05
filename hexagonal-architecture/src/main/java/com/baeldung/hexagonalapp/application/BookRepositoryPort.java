package com.baeldung.hexagonalapp.application;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryPort {
    BookDto save(BookDto book);
    Optional<BookDto> find(Long id);
    List<BookDto> findAll();
}
