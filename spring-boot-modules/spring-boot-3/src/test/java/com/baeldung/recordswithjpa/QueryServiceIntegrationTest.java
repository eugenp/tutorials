package com.baeldung.recordswithjpa;

import com.baeldung.recordswithjpa.entity.Book;
import com.baeldung.recordswithjpa.records.BookRecord;
import com.baeldung.recordswithjpa.repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QueryServiceIntegrationTest extends RecordsAsJpaIntegrationTest {

    @Autowired
    private QueryService queryService;


    @Test
    void findAllBooks() {
        List<BookRecord> allBooks = queryService.findAllBooks();
        assertEquals(3, allBooks.size());
    }

    @Test
    void findBookById() {
        BookRecord bookById = queryService.findBookById(1L);
        assertEquals("The Lord of the Rings", bookById.title());
    }

    @Test
    void findAllBooksUsingMapping() {
        List<BookRecord> allBooksUsingMapping = queryService.findAllBooksUsingMapping();
        assertEquals(3, allBooksUsingMapping.size());
    }
}