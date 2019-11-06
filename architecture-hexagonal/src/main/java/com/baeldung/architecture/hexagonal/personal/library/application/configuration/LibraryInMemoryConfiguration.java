package com.baeldung.architecture.hexagonal.personal.library.application.configuration;

import com.baeldung.architecture.hexagonal.personal.library.application.adapter.business.service.AddBookServiceImpl;
import com.baeldung.architecture.hexagonal.personal.library.application.adapter.business.service.GetBookServiceImpl;
import com.baeldung.architecture.hexagonal.personal.library.application.adapter.business.service.RemoveBookServiceImpl;
import com.baeldung.architecture.hexagonal.personal.library.application.adapter.facade.Library;
import com.baeldung.architecture.hexagonal.personal.library.application.adapter.facade.LibraryInMemory;
import com.baeldung.architecture.hexagonal.personal.library.application.adapter.infrastructure.InMemoryBookRepository;
import com.baeldung.architecture.hexagonal.personal.library.core.port.business.service.AddBookService;
import com.baeldung.architecture.hexagonal.personal.library.core.port.business.service.GetBookService;
import com.baeldung.architecture.hexagonal.personal.library.core.port.business.service.RemoveBookService;
import com.baeldung.architecture.hexagonal.personal.library.core.port.infrastructure.BookRepository;

public class LibraryInMemoryConfiguration {

    public static Library createLibraryInMemory() {
        BookRepository bookRepository = new InMemoryBookRepository();

        AddBookService addBookService = new AddBookServiceImpl(bookRepository);
        GetBookService getBookService = new GetBookServiceImpl(bookRepository);
        RemoveBookService removeBookService = new RemoveBookServiceImpl(bookRepository);

        return new LibraryInMemory(addBookService, getBookService, removeBookService);
    }
}
