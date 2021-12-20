package com.baeldung.hexagonalarchitecture.domain;

import java.util.List;

public interface IBookRepository {
    List<Book> findAllBooks();
}