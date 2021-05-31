package com.baeldung.hexarchitecture.application;

import com.baeldung.hexarchitecture.application.port.in.CreateBookCommand;
import com.baeldung.hexarchitecture.application.port.out.BookRepository;
import com.baeldung.hexarchitecture.domain.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("Book Service Tests")
@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    private Book book;

    private CreateBookCommand createBookCommand;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setAuthor("author");

        createBookCommand = new CreateBookCommand();
        createBookCommand.setAuthor("author");
    }

    @Test
    public void createBookShouldCallRepositoryCreateBookMethod() {
        bookService.createBook(createBookCommand);

        verify(bookRepository, times(1)).createBook(createBookCommand);
    }

    @Test
    public void getBookShouldReturnExpectedBook() {
        when(bookRepository.getBook(anyString())).thenReturn(book);

        Book returnedBook = bookService.getBook("bbok-1");

        assertEquals(book, returnedBook);
    }

}
