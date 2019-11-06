package com.baeldung.architecture.hexagonal.personal.library.core.port.business.service;

import com.baeldung.architecture.hexagonal.personal.library.core.domain.Book;

import java.util.List;

public interface GetBookService {
    List<Book> getAll();
}
