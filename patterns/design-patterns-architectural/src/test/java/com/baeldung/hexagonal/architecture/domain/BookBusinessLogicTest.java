package com.baeldung.hexagonal.architecture.domain;

import com.baeldung.hexagonal.architecture.port.out.BookRepository;
import com.baeldung.hexagonal.architecture.port.out.BookWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class BookBusinessLogicTest {

    private BookWriter writer;
    private BookRepository bookRepository;
    private BookBusinessLogic bookBusinessLogic;

    @BeforeEach
    public void setUp() {
        bookRepository = mock(BookRepository.class);
        writer = mock(BookWriter.class);
        bookBusinessLogic = new BookBusinessLogic(bookRepository, writer);
    }

    @Test
    void whenGetAllBooks_generateRandomBooks_thenPrintToConsole() {
        bookBusinessLogic.getBooks();
        verify(bookRepository).getAllBooks();
        verify(writer).writeBooks(anyList());
    }

}