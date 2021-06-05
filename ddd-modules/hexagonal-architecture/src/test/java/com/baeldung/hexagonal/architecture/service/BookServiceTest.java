package com.baeldung.hexagonal.architecture.service;

import com.baeldung.hexagonal.architecture.repository.Book;
import com.baeldung.hexagonal.architecture.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class BookServiceTest {
    private static final String BOOK_NAME = "Spring Photography";
    private static final Integer BOOK_SHELF_NO = 5;

    private BookRepository bookRepository;
    private BookServicePort bookService;

    @BeforeEach
    public void init() {
        bookRepository = mock(BookRepository.class);
        bookService = new BookService(bookRepository);
    }

    @Test
    public void givenNullNameAndShelfNo_whenFindBook_thenThrowIllegalArgumentException() {

        assertThatThrownBy(() -> bookService.findBook(null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name and/or shelfNo are null");
    }

    @Test
    public void givenNameAndShelfNo_whenFindBookIsEmpty_thenThrowRuntimeException() {

        given(bookRepository.findByNameAndShelfNo(BOOK_NAME, BOOK_SHELF_NO))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.findBook(BOOK_NAME, BOOK_SHELF_NO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Book not found");
    }

    @Test
    public void givenNameAndShelfNo_whenFindBook_thenReturnBook() {

        Book entity = new Book(1, BOOK_NAME, BOOK_SHELF_NO);
        BookDTO expected = new BookDTO(entity);

        given(bookRepository.findByNameAndShelfNo(BOOK_NAME, BOOK_SHELF_NO))
                .willReturn(Optional.of(entity));

        BookDTO book = bookService.findBook(BOOK_NAME, BOOK_SHELF_NO);

        assertEquals(expected, book);
    }
}
