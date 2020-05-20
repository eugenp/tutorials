package com.baeldung.architecture.hexagonal.business.bordery.driven;

import java.util.List;

import com.baeldung.architecture.hexagonal.business.bordery.dto.BookDto;

public interface BookPersistencePort {

    List<BookDto> findByNameContaining(String partOfTheName);

    BookDto save(BookDto book);

}
