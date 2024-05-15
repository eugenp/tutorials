package com.baeldung.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.baeldung.web.dto.Book;

public class BookRepositoryUnitTest {

    private BookRepository repository;

    @Before
    public void setUp() {
        repository = new BookRepository();
    }

    @Test
    public void givenNoBooks_WhenFindById_ThenReturnEmptyOptional() {
        assertFalse(repository.findById(1).isPresent());
    }

    @Test
    public void givenOneMatchingBook_WhenFindById_ThenReturnEmptyOptional() {

        long id = 1;
        Book expected = bookWithId(id);

        repository.add(expected);

        Optional<Book> found = repository.findById(id);

        assertTrue(found.isPresent());
        assertEquals(expected, found.get());
    }

    private static Book bookWithId(long id) {
        Book book = new Book();
        book.setId(id);
        return book;
    }

    @Test
    public void givenOneNonMatchingBook_WhenFindById_ThenReturnEmptyOptional() {

        long id = 1;
        Book expected = bookWithId(id);

        repository.add(expected);

        Optional<Book> found = repository.findById(id + 1);

        assertFalse(found.isPresent());
    }
}