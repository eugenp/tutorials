package com.baeldung.hexagonal;

import static com.baeldung.util.LogerUtil.LOG;

import com.baeldung.hexagonal.bookstore.core.BookStore;
import com.baeldung.hexagonal.bookstore.core.BookStoreImpl;
import com.baeldung.hexagonal.bookstore.entity.Book;
import com.baeldung.hexagonal.bookstore.primaryport.BookFinder;
import com.baeldung.hexagonal.bookstore.primaryport.BookFinderMockAdaptor;
import com.baeldung.hexagonal.bookstore.secondaryport.BookStoreMemoryRepository;
import com.baeldung.hexagonal.bookstore.secondaryport.BookStoreRepository;

public class HexagonalPatternDriver {

    public static void main(String args[]) {

        BookStoreRepository bookStoreMockRepository = new BookStoreMemoryRepository();

        BookStore bookStore = new BookStoreImpl(bookStoreMockRepository);

        BookFinder bookFinderMock = new BookFinderMockAdaptor(bookStore);

        Book findBook = bookFinderMock.findBook("9780062312686");

        LOG.info("Title of book is: " + findBook.getTitle());

        LOG.info("Author of book is: " + findBook.getAuthorName());

    }
}
