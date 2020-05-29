package com.baeldung.hexagonal.architecture.domain;

import com.baeldung.hexagonal.architecture.output.BookRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceDomainTest {

    private BookRepository bookRepository;
    private BookServiceDomain bookServiceDomain;

    @BeforeEach
    public void setUp() {
        bookRepository = mock(BookRepository.class);
        bookServiceDomain = new BookServiceDomain(bookRepository);
    }

    @Test
    void whenBookCreated_shouldSaveBook_thenReturnBookId() {
        Book book = new Book(UUID.randomUUID(), "id", "title", "author");
        UUID id = bookServiceDomain.createBook(book);

        verify(bookRepository).saveBook(book);
        assertThat(id).isNotNull();
    }

    @Test
    void whenBookUpdated_shouldSaveBook() {
        Book book = new Book(UUID.randomUUID(), "id", "title", "author");
        bookServiceDomain.updateBook(book);

        verify(bookRepository).saveBook(book);
    }

    @Test
    void givenBookExits_whenFindByBookId_shouldRetrieveBook() {
        UUID id1 = UUID.randomUUID();
        Book book1 = new Book(id1, "isbn1", "title1", "author1");
        UUID id2 = UUID.randomUUID();
        Book book2 = new Book(id2, "isbn2", "title2", "author2");
        UUID id3 = UUID.randomUUID();
        Book book3 = new Book(id3, "isbn3", "title3", "author3");

        when(bookRepository.getBookById(id1)).thenReturn(book1);
        when(bookRepository.getBookById(id2)).thenReturn(book2);
        when(bookRepository.getBookById(id3)).thenReturn(book3);

        assertThat(bookServiceDomain.getBookById(id1)).isEqualTo(book1);
        assertThat(bookServiceDomain.getBookById(id2)).isEqualTo(book2);
        assertThat(bookServiceDomain.getBookById(id3)).isEqualTo(book3);
    }
}