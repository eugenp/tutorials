package com.baeldung.hexagonal.architecture.service;

public interface BookServicePort {
    BookDTO findBook(final String name, final Integer shelfNo);
}
