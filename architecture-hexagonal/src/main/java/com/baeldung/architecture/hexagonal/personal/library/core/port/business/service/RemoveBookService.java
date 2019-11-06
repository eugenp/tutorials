package com.baeldung.architecture.hexagonal.personal.library.core.port.business.service;

public interface RemoveBookService {

    boolean removeBook(String isbn);

    void removeAllBooks();
}
