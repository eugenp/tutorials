package com.baeldung.spring.data.persistence.saveperformance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BookApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookApplication.class);

    @Autowired
    private BookRepository bookRepository;

    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void executePerformanceBenchmark() {

        int bookCount = 10000;

        long start = System.currentTimeMillis();
        for (int i = 0; i < bookCount; i++) {
            bookRepository.save(new Book("Book " + i, "Author " + i));
        }
        long end = System.currentTimeMillis();
        bookRepository.deleteAll();

        LOGGER.debug("It took {}ms to execute save() for {} books.", (end - start), bookCount);

        List<Book> bookList = new ArrayList<>();
        for (int i = 0; i < bookCount; i++) {
            bookList.add(new Book("Book " + i, "Author " + i));
        }

        start = System.currentTimeMillis();
        bookRepository.saveAll(bookList);
        end = System.currentTimeMillis();

        LOGGER.debug("It took {}ms to execute saveAll() with {}} books.", (end - start), bookCount);

    }
}
