package com.baeldung.architecture.hexagonal.personal.library.application.adapter.facade;

import com.baeldung.architecture.hexagonal.personal.library.core.domain.Book;
import com.baeldung.architecture.hexagonal.personal.library.core.port.business.service.AddBookService;
import com.baeldung.architecture.hexagonal.personal.library.core.port.business.service.GetBookService;
import com.baeldung.architecture.hexagonal.personal.library.core.port.business.service.RemoveBookService;

import java.util.List;

public class LibraryInMemory implements Library {

    private final AddBookService addBookService;
    private final GetBookService getBookService;
    private final RemoveBookService removeBookService;

    public LibraryInMemory(AddBookService addBookService, GetBookService getBookService, RemoveBookService removeBookService) {
        this.addBookService = addBookService;
        this.getBookService = getBookService;
        this.removeBookService = removeBookService;
    }

    @Override
    public void addBook(Book book) {
        addBookService.addBook(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return getBookService.getAll();
    }

    @Override
    public void removeBook(String isbn) {
        removeBookService.removeBook(isbn);
    }

    @Override
    public void removeAllBooks() {
        removeBookService.removeAllBooks();
    }
}
