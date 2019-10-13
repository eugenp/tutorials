package com.baeldung.hexagonalarchitecture.core.service;

import com.baeldung.hexagonalarchitecture.core.domain.Book;

public interface BookService {
    Book getBook(Long id);
}
