package com.baeldung.spring.redis.configuration.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.spring.redis.configuration.entity.Book;
import com.baeldung.spring.redis.configuration.repository.BooksRepository;

@RunWith(MockitoJUnitRunner.class)
public class BooksControllerUnitTest {

    @InjectMocks
    private BooksController booksController;

    @Mock
    private BooksRepository booksRepository;

    private static final Long BOOK_ID = 1l;
    private static final Long OTHER_BOOK_ID = 2l;
    private static final String BOOK_NAME = "Ulysses";
    private static final String ADDED = "Added";

    @Test
    public void whenNewBook_thenCorrect() {
        assertEquals(ADDED, booksController.newBook(new Book(BOOK_ID, BOOK_NAME)));
    }

    @Test
    public void whenFindOneFinds_thenCorrect() {
        Book book = new Book(BOOK_ID, BOOK_NAME);
        when(booksRepository.findById(BOOK_ID)).thenReturn(book);
        assertSame(book, booksController.findOne(BOOK_ID));
    }

    @Test
    public void whenFindOneNotFound_thenReturnsNull() {
        Book book = new Book(BOOK_ID, BOOK_NAME);
        when(booksRepository.findById(BOOK_ID)).thenReturn(book);
        assertNull(booksController.findOne(OTHER_BOOK_ID));
    }

}
