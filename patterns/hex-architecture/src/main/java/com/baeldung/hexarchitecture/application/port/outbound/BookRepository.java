package com.baeldung.hexarchitecture.application.port.outbound;

import com.baeldung.hexarchitecture.application.port.inbound.CreateBookCommand;
import com.baeldung.hexarchitecture.domain.Book;

public interface BookRepository {

    void createBook(CreateBookCommand createBookCommand);
    Book getBook(String id);
}
