package com.baeldung;

import static com.baeldung.util.LogerUtil.LOG;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.baeldung.hexagonal.bookstore.core.BookStore;
import com.baeldung.hexagonal.bookstore.core.BookStoreImpl;
import com.baeldung.hexagonal.bookstore.entity.Book;
import com.baeldung.hexagonal.bookstore.primaryport.BookFinder;
import com.baeldung.hexagonal.bookstore.primaryport.BookFinderMockAdaptor;
import com.baeldung.hexagonal.bookstore.secondaryport.BookStoreMemoryRepository;
import com.baeldung.hexagonal.bookstore.secondaryport.BookStoreRepository;

public class HexagonalPatternUnitTest {

    @Test
    public void givenValidISBNNumber_WhenBookFindingInDatabase_thenSuccessfullyFoundBook() {

        BookStoreRepository bookStoreMemoryRepository = new BookStoreMemoryRepository();

        BookStore bookStore = new BookStoreImpl(bookStoreMemoryRepository);

        BookFinder bookFinder = new BookFinderMockAdaptor(bookStore);

        Book findBook = bookFinder.findBook("9780062312686");

        LOG.info("Title of book is: " + findBook.getTitle());

        LOG.info("Author of book is: " + findBook.getAuthorName());

        assertEquals("The Intelligent Investor", findBook.getTitle());
        assertEquals("Benjamin Graham", findBook.getAuthorName());

    }
}
