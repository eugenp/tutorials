package com.baeldung.hexagonalapp.application;

public class BookNotFound extends RuntimeException {
    public BookNotFound() {
        super("The book you're looking for does not exist");
    }
}
