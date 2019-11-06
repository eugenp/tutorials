package com.baeldung.architecture.hexagonal.personal.library.core.domain;

import com.baeldung.architecture.hexagonal.personal.library.core.domain.exception.BookValidationException;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Book implements Serializable {
    private final String isbn;
    private final String name;
    private List<Author> authors;

    public Book(String isbn, String name, Author... authors) {
        this.isbn = isbn;
        this.name = name;
        this.authors = Arrays.asList(authors);
    }

    public void validateRequiredFields(){
        if(StringUtils.isBlank(isbn)){
            throw new BookValidationException("ISBN is required for the Book");
        }
    }

    public String getIsbn() {
        return isbn;
    }

    public String getName() {
        return name;
    }

    public List<Author> getAuthors() {
        return authors;
    }
}
