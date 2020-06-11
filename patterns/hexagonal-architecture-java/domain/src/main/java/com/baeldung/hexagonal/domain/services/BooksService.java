package com.baeldung.hexagonal.domain.services;

import java.util.UUID;

public interface BooksService {

    UUID createBook(String name, String author, String description);

    void deleteBook(UUID id);

}