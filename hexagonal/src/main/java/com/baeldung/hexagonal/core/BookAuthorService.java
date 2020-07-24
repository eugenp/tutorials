package com.baeldung.hexagonal.core;

import com.baeldung.hexagonal.domain.AuthorDto;
import com.baeldung.hexagonal.domain.BookDto;

import java.util.List;

public interface BookAuthorService {

    void addBook(BookDto bookDto);
    List<BookDto> getAllBook();

    void addAuthor(AuthorDto authorDto);
    List<AuthorDto> getAllAuthor();

}
