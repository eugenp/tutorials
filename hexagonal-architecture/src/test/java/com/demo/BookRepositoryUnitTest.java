package com.demo;

import com.demo.models.Book;
import com.demo.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookRepositoryUnitTest {

    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
    }

    @Test
    void shouldFindById_thenReturnBook() {
        final UUID id = UUID.randomUUID();
        final Book book = createBook(id);
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        final Optional<Book> result = bookRepository.findById(id);

        assertEquals(book, result.get());
    }

    @Test
    void shouldSaveBook_viaBookCrudRepository() {
        final UUID id = UUID.randomUUID();
        final Book book = createBook(id);

        bookRepository.save(book);

        verify(bookRepository).save(book);
    }

    private Book createBook(UUID id) {
        return new Book(id, "title-" + id.toString(), "author-" + id.toString(), "isbn-" + id.toString());
    }
}
