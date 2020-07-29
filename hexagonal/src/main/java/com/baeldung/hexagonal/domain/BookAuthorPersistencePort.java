package com.baeldung.hexagonal.domain;

import java.util.List;

public interface BookAuthorPersistencePort {

    void addBook(BookDto bookDto);

    List<BookDto> getAllBook();

    void addAuthor(AuthorDto authorDto);

    List<AuthorDto> getAllAuthor();

}
