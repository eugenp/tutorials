package org.baeldung.hexa.domain;

import static org.junit.Assert.assertThat;

import java.util.List;

import org.baeldung.hexa.repo.InMemoryRepositoryImpl;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class BookServiceITest {

    private BookService underTest;
    
    @Before
    public void init() {
        BookRepository bookRepository = new InMemoryRepositoryImpl();
        underTest = new BookServiceImpl(bookRepository);
    }
    
    
    @Test
    public void givenAuthor_whenGetByAuthor_thenSuccess() {
        // prepare
        Book book1 = new Book("lorem", "ipsum");
        Book book2 = new Book("dolor", "ipsum");
        ImmutableList<Book> mockedBooks = ImmutableList.of(book1, book2);
        underTest.storeBook(book1);
        underTest.storeBook(book2);

        // act
        List<Book> actual = underTest.getBooksByAuthor("ipsum");

        // assert
        assertThat(actual.isEmpty(), Matchers.is(false));
        assertThat(actual.equals(mockedBooks), Matchers.is(true));

    }
    
}
