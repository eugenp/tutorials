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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LibrarySpringConfiguration {

    @Bean
    BookRepository bookRepository() {
        return new InMemoryBookRepository();
    }

    @Bean
    AddBookService addBookService(BookRepository bookRepository) {
        return new AddBookServiceImpl(bookRepository);
    }

    @Bean
    GetBookService getBookService(BookRepository bookRepository) {
        return new GetBookServiceImpl(bookRepository);
    }

    @Bean
    RemoveBookService removeBookService(BookRepository bookRepository) {
        return new RemoveBookServiceImpl(bookRepository);
    }

    @Bean
    Library library(AddBookService addBookService, GetBookService getBookService, RemoveBookService removeBookService){
        return new LibraryInMemory(addBookService, getBookService, removeBookService);
    }

}
