package com.baeldung.hexagonalarchitecture.util;

import com.baeldung.hexagonalarchitecture.core.domain.Book;
import com.baeldung.hexagonalarchitecture.core.port.BookRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseUtil implements CommandLineRunner {

    @Autowired
    private BookRepositoryPort bookRepositoryPort;

    @Override
    public void run(String... args) throws Exception {
        bookRepositoryPort.insertBook(new Book("Brocchus"));
    }
}
