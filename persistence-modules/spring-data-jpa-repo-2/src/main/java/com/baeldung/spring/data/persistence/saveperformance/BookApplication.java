package com.baeldung.spring.data.persistence.saveperformance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BookApplication {

    @Autowired
    private BookRepository bookRepository;

    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void executePerformanceBenchmark() {

        int bookCount = 10000;

        long start = System.currentTimeMillis();
        for(int i = 0; i < bookCount; i++) {
            bookRepository.save(new Book("Book " + i, "Author " + i));
        }
        long end = System.currentTimeMillis();
        bookRepository.deleteAll();

        System.out.println("It took " + (end - start) + "ms to execute save() for " + bookCount + " books");

        List<Book> bookList = new ArrayList<>();
        for (int i = 0; i < bookCount; i++) {
            bookList.add(new Book("Book " + i, "Author " + i));
        }

        start = System.currentTimeMillis();
        bookRepository.saveAll(bookList);
        end = System.currentTimeMillis();

        System.out.println("It took " + (end - start) + "ms to execute saveAll() with " + bookCount + " books\n");

    }
}
