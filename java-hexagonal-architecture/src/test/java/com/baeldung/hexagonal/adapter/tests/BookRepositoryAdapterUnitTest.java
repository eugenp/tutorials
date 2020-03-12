package com.baeldung.hexagonal.adapter.tests;

import org.junit.Assert;
import org.junit.Test;

import com.baeldung.hexagonal.adapter.BookRepositoryAdapter;
import com.baeldung.hexagonal.model.Book;

public class BookRepositoryAdapterUnitTest {
    private final BookRepositoryAdapter adapter = new BookRepositoryAdapter();

    @Test
    public void givenBook_thenSaveTheBook_ThenRetrieveTheBook() {
        // create the book
        Book book = new Book("Hello World", "John Doe");
        
        // save the book
        this.adapter.saveBook(book);
        
        // retrieve the book
        Book retrievedBook = this.adapter.getBook("Hello World");
        
        // assert
        Assert.assertEquals(book, retrievedBook);
    }
}
