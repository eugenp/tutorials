package com.baeldung.recordswithjpa;

import com.baeldung.recordswithjpa.entity.Book;
import com.baeldung.recordswithjpa.repository.BookRepository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;

@Transactional
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RecordsAsJpaIntegrationTest {
    @Autowired
    protected BookRepository bookRepository;

    @BeforeAll
    void setUp() {

        Book book = new Book(null,"The Lord of the Rings", "J.R.R. Tolkien", "978-0544003415");
        Book book2 = new Book(null,"The Hobbit", "J.R.R. Tolkien", "978-0547928227");
        Book book3 = new Book(null,"Harry Potter and the Philosopher's Stone", "J.K. Rowling", "978-0747532699");


        bookRepository.save(book);
        bookRepository.save(book2);
        bookRepository.save(book3);
    }

    @AfterAll
    void tearDown() {
        bookRepository.deleteAll();
    }
}
