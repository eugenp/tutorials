package com.baeldung.architecture.hexagonal.personal.library.core.port.business.service;

import com.baeldung.architecture.hexagonal.personal.library.core.domain.Book;

public interface AddBookService {

    Book addBook(Book book);
}
