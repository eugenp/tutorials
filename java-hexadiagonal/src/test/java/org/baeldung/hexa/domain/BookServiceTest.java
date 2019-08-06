package org.baeldung.hexa.domain;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.google.common.collect.ImmutableList;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    BookService underTest;

    @Mock
    BookRepository mockedRepo;

    @Before
    public void init() {
        underTest = new BookServiceImpl(mockedRepo);
    }

    @Test
    public void givenBookOnce_whenStore_thenSuccess() {
        // prepare
        Book input = new Book("lorem", "ipsum");
        // act
        underTest.storeBook(input);
        // assert
        Mockito.verify(mockedRepo, Mockito.atLeastOnce())
            .storeBook(input);
    }

    @Test
    public void givenBookExists_whenStore_thenDoNotStore() {
        // prepare
        Book input1 = new Book("lorem", "ipsum");
        Mockito.when(mockedRepo.getBooksByAuthor("ipsum"))
            .thenReturn(ImmutableList.of(input1));
        // act
        underTest.storeBook(input1);
        // assert
        Mockito.verify(mockedRepo, Mockito.times(0))
            .storeBook(input1);
    }

    @Test
    public void givenBooks_whenGetAuthorCount_thenSuccess() {
        // prepare
        ImmutableList<Book> mockedBooks = ImmutableList.of(new Book("lorem", "ipsum"), new Book("dolor", "ipsum"));
        Mockito.when(mockedRepo.getAllBooks())
            .thenReturn(mockedBooks);

        // act
        int numberOfAuthors = underTest.getNumberOfAuthors();

        // assert
        assertThat(numberOfAuthors, Matchers.is(1));
    }

    @Test
    public void givenBooks_whenGetNumberOfBooks_thenSuccess() {
        // prepare
        ImmutableList<Book> mockedBooks = ImmutableList.of(new Book("lorem", "ipsum"), new Book("dolor", "ipsum"));
        Mockito.when(mockedRepo.getAllBooks())
            .thenReturn(mockedBooks);

        // act
        int numberOfBooks = underTest.getNumberOfBooks();

        // assert
        assertThat(numberOfBooks, Matchers.is(2));
    }

}
