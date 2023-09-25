package com.baeldung.recordswithjpa.repository;

import com.baeldung.recordswithjpa.RecordsAsJpaEmbeddableIntegrationTest;
import com.baeldung.recordswithjpa.embeddable.Author;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmbeddableBookRepositoryIntegrationTest extends RecordsAsJpaEmbeddableIntegrationTest {

    @Test
    void findBookByAuthor() {
        assertEquals(2, bookRepository.findBookByAuthor(new Author("J.R.R.", "Tolkien")).size());
    }

}
