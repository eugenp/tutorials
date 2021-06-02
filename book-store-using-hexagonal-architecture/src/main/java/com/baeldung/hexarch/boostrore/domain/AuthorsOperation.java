package com.baeldung.hexarch.boostrore.domain;

import com.baeldung.hexarch.boostrore.model.Author;

public interface AuthorsOperation {
    Author create(String firstName, String lastName, String emailId);

    Author getAuthorByEmailId(final String emailId);

    Author get(final long id);

    void delete(final String emailId);

    void delete(final long id);
}
