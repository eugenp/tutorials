package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.domain.models.Book;
import com.baeldung.hexagonal.domain.ports.BookValidator;

public class BookValidatorAdapter implements BookValidator {

    private static final String REGEX = "^[a-z A-Z]+$";

    @Override
    public boolean validate(Book book) {
        return validTitle(book.getTitle()) && validAuthor(book.getAuthor());
    }

    private boolean validTitle(String title) {
        return title != null && title.matches(REGEX);
    }

    private boolean validAuthor(String author) {
        return author != null && author.length() <= 50 && author.matches(REGEX);
    }
}
