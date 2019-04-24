package com.baeldung.hexagonal.domain.ports;

import com.baeldung.hexagonal.domain.models.Book;

public interface BookValidator {

    boolean validate(Book book);
}
