package com.baeldung.hexarch.boostrore.domain;

import com.baeldung.hexarch.boostrore.model.Author;
import com.baeldung.hexarch.boostrore.model.Book;

import java.util.Set;

public interface BookOperations {
    Book create(final String isbn, final String bookName, Set<String> authorEmails);

    Book getBookById(final long id);

    Book getBookByIsbn(final String isbn);

    Set<Author> getAuthorsOfBook(final long id);
}
