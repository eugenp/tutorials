package com.baeldung.boot.hexagonal.infrastructure.adapters;

import com.baeldung.boot.hexagonal.domain.models.Book;
import com.baeldung.boot.hexagonal.domain.ports.service.BooksTestUtility;
import com.baeldung.boot.hexagonal.infrastructure.BookJpaRepository;
import com.baeldung.boot.hexagonal.infrastructure.entities.BookEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@Slf4j
@DisplayName("JPA Adapter Unit Tests")
class BookJpaPersistenceAdapterUnitTest {

    private static List<Book> books;
    @InjectMocks
    private BookJpaPersistenceAdapter bookJpaPersistenceAdapter;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private BookJpaRepository bookJpaRepository;

    @BeforeAll
    private static void populateBooks() {
        log.info("Setting up the unit tests for BookJpaPersistenceAdapter.");
        books = BooksTestUtility.populateBooks();
    }

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Check add book")
    void whenCalledAddBook_thenVerify() {
        Book book = books.get(0);
        BookEntity bookEntity = new ModelMapper().map(book, BookEntity.class);

        doNothing().when(modelMapper).map(bookEntity, book);
        when(bookJpaRepository.save(any(BookEntity.class))).thenReturn(bookEntity);

        bookJpaPersistenceAdapter.addBook(book);
        assertEquals(bookEntity.getTitle(), book.getTitle());
    }

    @Test
    @DisplayName("Check get book")
    void givenBookId_whenCalledGetBook_thenCheck() {
        Book book = books.get(0);
        BookEntity bookEntity = new ModelMapper().map(book, BookEntity.class);

        when(bookJpaRepository.findById(1L)).thenReturn(Optional.of(bookEntity));

        bookJpaPersistenceAdapter.getBook(0);
        assertEquals(bookEntity.getTitle(), book.getTitle());
    }

}
