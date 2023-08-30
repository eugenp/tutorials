package com.baeldung.recordswithjpa;

import com.baeldung.recordswithjpa.records.BookRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
        BookRecord bookByTitle = queryService.findBookByTitle("The Lord of the Rings");
        assertNotNull(bookByTitle);
    }

    @Test
    void findAllBooksUsingMapping() {
        List<BookRecord> allBooksUsingMapping = queryService.findAllBooksUsingMapping();
        assertEquals(3, allBooksUsingMapping.size());
    }
}