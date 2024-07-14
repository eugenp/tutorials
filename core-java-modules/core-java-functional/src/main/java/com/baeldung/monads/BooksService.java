package com.baeldung.monads;

import com.baeldung.monads.AuthorsRepository.AuthorNotFoundException;

import io.vavr.control.Try;

public class BooksService {

    void validateBookImperativeStyle(Book book) {
        if (!validIsbn(book.getIsbn())) {
            throw new IllegalArgumentException("Invalid ISBN");
        }
        Author author = AuthorsRepository.findById(book.getAuthorId());
        if (author == null) {
            throw new AuthorNotFoundException("Author not found");
        }
        if (!author.getGenres()
            .contains(book.genre())) {
            throw new IllegalArgumentException("Author does not write in this genre");
        }
    }

    void validateBookFunctionalStyle(Book bookToValidate) {
        Try.ofSupplier(() -> bookToValidate)
            .andThen(book -> validateIsbn(book.getIsbn()))
            .map(book -> fetchAuthor(book.getAuthorId()))
            .andThen(author -> validateBookGenre(bookToValidate.genre(), author))
            .get();
    }

    private void validateBookGenre(String genre, Author author) {
        if (!author.getGenres()
            .contains(genre)) {
            throw new IllegalArgumentException("Author does not write in this genre");
        }
    }

    private Author fetchAuthor(Long authorId) {
        Author author = AuthorsRepository.findById(authorId);
        if (author == null) {
            throw new AuthorNotFoundException("Author not found");
        }
        return author;
    }

    private void validateIsbn(String isbn) {
        if (!validIsbn(isbn)) {
            throw new IllegalArgumentException("Invalid ISBN");
        }
    }

    private boolean validIsbn(String isbn) {
        return isbn.length() == 13 && isbn.startsWith("978");
    }
}
