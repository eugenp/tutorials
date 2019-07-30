package com.baeldung.hexagonal.architecture.application.control;

import com.baeldung.hexagonal.architecture.application.entity.Book;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BookServiceTest {

    private BookService bookService;
    private MockBookRepository bookRepository;

    @Before
    public void setUp() {
        bookRepository = new MockBookRepository();
        bookService = new BookService(bookRepository);
    }

    @Test
    public void whenBookIsOk_Test() {
        Book book = new Book();
        book.setAuthor("Stephen King");
        book.setTitle("IT");

        bookService.registerBook(book);

        assertThat(bookRepository.mockDatabase).contains(book);
    }
}
