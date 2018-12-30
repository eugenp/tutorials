package com.baeldung;

import static com.baeldung.util.LogerUtil.LOG;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.baeldung.hexagonal.bookstore.core.BookStore;
import com.baeldung.hexagonal.bookstore.core.BookStoreImpl;
import com.baeldung.hexagonal.bookstore.model.Book;
import com.baeldung.hexagonal.bookstore.primaryport.BookFinder;
import com.baeldung.hexagonal.bookstore.primaryport.ConsoleBookFinderAdaptor;
import com.baeldung.hexagonal.bookstore.secondaryport.BookStoreDatabaseRepository;
import com.baeldung.hexagonal.bookstore.secondaryport.BookStoreRepository;

public class HexagonalPatternIntegrationTest {
    @Test
    public void givenValidISBNNumber_WhenBookFindingInDatabase_thenSuccessfullyFoundBook() {

        BookStoreRepository bookStoreDatabaseRepository = new BookStoreDatabaseRepository();

        BookStore bookStore = new BookStoreImpl(bookStoreDatabaseRepository);

        BookFinder bookFinder = new ConsoleBookFinderAdaptor(bookStore);

        Book findBook = bookFinder.findBook("9780062312686");

        LOG.info("Title of book is: " + findBook.getTitle());

        LOG.info("Author of book is: " + findBook.getAuthorName());

        assertEquals("The Intelligent Investor", findBook.getTitle());
        assertEquals("Benjamin Graham", findBook.getAuthorName());

    }
}
