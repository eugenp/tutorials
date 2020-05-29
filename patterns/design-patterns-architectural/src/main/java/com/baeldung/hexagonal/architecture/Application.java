package com.baeldung.hexagonal.architecture;

import com.baeldung.hexagonal.architecture.adapter.input.CliApi;
import com.baeldung.hexagonal.architecture.adapter.output.InMemoryBookRepositoryImpl;
import com.baeldung.hexagonal.architecture.domain.BookServiceDomain;
import com.baeldung.hexagonal.architecture.input.BookService;
import com.baeldung.hexagonal.architecture.output.BookRepository;

public class Application {

    public static void main(String[] args) {
        BookRepository inMemoryBookRepository = new InMemoryBookRepositoryImpl();
        BookService bookService = new BookServiceDomain(inMemoryBookRepository);

//        WebApi webApi = new WebApi(bookService);
//        webApi.createBook("1", "A book", "Sample author");

        CliApi api = new CliApi(bookService);
        api.createBook();
    }
}
