package com.baeldung.hexagonal.architecture.port;

import com.baeldung.hexagonal.architecture.core.domain.Book;

public interface BookService {

    void create(Book book);

    void delete(long id);

    Book get(long id);
}
