package com.baeldung.recordswithjpa.repository;

import com.baeldung.recordswithjpa.entity.CompositeBook;
import com.baeldung.recordswithjpa.records.BookId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CompositeBookRepositoryIntegrationTest {
    @Autowired
    private CompositeBookRepository compositeBookRepository;

    @Test
    public void givenCompositeBook_whenSave_thenSaveToDatabase() {
        CompositeBook compositeBook = new CompositeBook(new BookId(1L, 1234567890L),
      "Book Title", "Author Name");
        compositeBookRepository.save(compositeBook);

        CompositeBook savedBook = compositeBookRepository
          .findById(new BookId(1L, 1234567890L))
          .orElse(null);
        assertNotNull(savedBook);
        assertEquals("Book Title", savedBook.getTitle());
        assertEquals("Author Name", savedBook.getAuthor());
    }
}