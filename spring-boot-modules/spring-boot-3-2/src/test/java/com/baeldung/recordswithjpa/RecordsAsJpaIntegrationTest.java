package com.baeldung.recordswithjpa;

import com.baeldung.recordswithjpa.entity.Book;
import com.baeldung.recordswithjpa.repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RecordsAsJpaIntegrationTest {
    @Autowired
    protected BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        Book book = new Book(1L,"The Lord of the Rings", "J.R.R. Tolkien", "978-0544003415");
        Book book2 = new Book(2L,"The Hobbit", "J.R.R. Tolkien", "978-0547928227");
        Book book3 = new Book(3L,"Harry Potter and the Philosopher's Stone", "J.K. Rowling", "978-0747532699");

        bookRepository.save(book);
        bookRepository.save(book2);
        bookRepository.save(book3);
    }

    @AfterEach
    void tearDown() {
        bookRepository.deleteAll();
    }
}
