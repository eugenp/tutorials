package com.baeldung.architecture.hexagonal.personal.library.application.configuration;

import com.baeldung.architecture.hexagonal.personal.library.application.adapter.business.service.AddBookServiceImpl;
import com.baeldung.architecture.hexagonal.personal.library.application.adapter.business.service.GetBookServiceImpl;
import com.baeldung.architecture.hexagonal.personal.library.application.adapter.business.service.RemoveBookServiceImpl;
import com.baeldung.architecture.hexagonal.personal.library.application.adapter.facade.Library;
import com.baeldung.architecture.hexagonal.personal.library.application.adapter.facade.LibraryInMemory;
import com.baeldung.architecture.hexagonal.personal.library.application.adapter.infrastructure.HazelcastBookRepository;
import com.baeldung.architecture.hexagonal.personal.library.core.port.infrastructure.BookRepository;

public class LibraryHazelcastConfiguration {

    public static Library createLibraryWithHazelcast() {
        BookRepository bookRepository = new HazelcastBookRepository();

        AddBookServiceImpl addBookService = new AddBookServiceImpl(bookRepository);
        GetBookServiceImpl getBookService = new GetBookServiceImpl(bookRepository);
        RemoveBookServiceImpl removeBookService = new RemoveBookServiceImpl(bookRepository);

        return new LibraryInMemory(addBookService, getBookService, removeBookService);
    }
}
