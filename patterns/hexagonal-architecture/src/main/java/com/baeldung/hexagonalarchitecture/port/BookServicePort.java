package com.baeldung.hexagonalarchitecture.port;

import com.baeldung.hexagonalarchitecture.core.domain.Book;

public interface BookServicePort {
    Book getBook(Long id);
}
