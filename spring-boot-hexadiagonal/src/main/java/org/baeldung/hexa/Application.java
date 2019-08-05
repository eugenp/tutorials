package org.baeldung.hexa;

import java.io.IOException;

import org.baeldung.hexa.cli.CliTransformer;
import org.baeldung.hexa.domain.BookRepository;
import org.baeldung.hexa.domain.BookService;
import org.baeldung.hexa.domain.BookServiceImpl;
import org.baeldung.hexa.repo.InMemoryRepositoryTransformer;

public class Application {

    public static void main(String[] args) throws IOException {
        
        BookRepository bookRepository = new InMemoryRepositoryTransformer();
        BookService bookService = new BookServiceImpl(bookRepository);
        CliTransformer cliTransformer = new CliTransformer(bookService);
        cliTransformer.processUserInput();
        
    }
}
