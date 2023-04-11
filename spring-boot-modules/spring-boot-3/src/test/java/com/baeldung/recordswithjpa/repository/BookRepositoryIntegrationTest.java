package com.baeldung.recordswithjpa.repository;

import com.baeldung.recordswithjpa.RecordsAsJpaIntegrationTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookRepositoryIntegrationTest extends RecordsAsJpaIntegrationTest {

    @Test
    void findBookByAuthor() {
       assertEquals(2, bookRepository.findBookByAuthor("J.R.R. Tolkien").size());
    }

    @Test
    void findBookById() {
        assertEquals("The Lord of the Rings", bookRepository.findBookById(1L).title());
    }
}