package com.baeldung.spring.data.jpa.optionalfields.sqlmapping;

import java.util.List;

import com.baeldung.spring.data.jpa.optionalfields.BookDto;

public interface BookCustomRepository {
    List<BookDto> fetchBooks();
}
