package com.baeldung.hexagonalarchitecture.core.port;

import com.baeldung.hexagonalarchitecture.core.domain.Book;

public interface BookRepositoryPort {
    Book getBook(Long id);

    void insertBook(Book book);
}
