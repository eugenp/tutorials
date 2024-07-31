package com.baeldung.recordswithjpa;

import com.baeldung.recordswithjpa.embeddable.Author;
import com.baeldung.recordswithjpa.embeddable.EmbeddableBook;
import com.baeldung.recordswithjpa.repository.EmbeddableBookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RecordsAsJpaEmbeddableIntegrationTest {
    @Autowired
    protected EmbeddableBookRepository bookRepository;

    @BeforeEach
    void setUp() {

        Author author = new Author("J.R.R.", "Tolkien");
        EmbeddableBook book1 = new EmbeddableBook(null, "The Lord of the Rings", author, "978-0544003415");
        EmbeddableBook book2 = new EmbeddableBook(null, "The Hobbit", author, "978-0547928227");

        bookRepository.save(book1);
        bookRepository.save(book2);
    }

    @AfterEach
    void tearDown() {
        bookRepository.deleteAll();
    }
}
