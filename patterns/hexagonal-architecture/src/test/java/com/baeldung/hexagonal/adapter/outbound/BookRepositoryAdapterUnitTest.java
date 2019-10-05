package com.baeldung.hexagonal.adapter.outbound;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.hexagonal.HexagonalApplication;
import com.baeldung.hexagonal.domain.Book;
import com.baeldung.hexagonal.repository.BookEntity;
import com.baeldung.hexagonal.repository.BookRepository;

import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@ActiveProfiles("test-book-repo")
@SpringBootTest(classes = HexagonalApplication.class)
public class BookRepositoryAdapterUnitTest extends TestCase {

    @Autowired
    private BookRepositoryAdapter bookRepositoryAdapter;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void givenBook_whenAddingBook_ThenSuccess() {
        // assign - given
        BookEntity bookEntity = new BookEntity("java", "abc", 100);
        Book book = new Book("java", "abc", 100);
        when(bookRepository.saveAndFlush(any(BookEntity.class))).thenReturn(bookEntity);

        // act - when
        bookRepositoryAdapter.addBook(book);

        // assert - then
        verify(bookRepository, timeout(1)).saveAndFlush(any(BookEntity.class));
    }

    @Test
    public void givenBookExist_whenSearchingBook_ThenGotSuccess() {
        // assign - given
        BookEntity bookEntity = new BookEntity("java", "abc", 100);
        when(bookRepository.findByName(anyString())).thenReturn(bookEntity);

        // act - when
        Book response = bookRepositoryAdapter.getBook("java");

        // assert - then
        assertNotNull(response);
        assertEquals("java", response.getName());
        assertEquals("abc", response.getAuthor());
        assertEquals(100, response.getPrice());
        verify(bookRepository, timeout(1)).findByName(anyString());
    }

    @Test
    public void givenBookDoesntExist_whenSearchingBook_ThenResponseIsNull() {
        // assign - given
        when(bookRepository.findByName(anyString())).thenReturn(null);

        // act - when
        Book response = bookRepositoryAdapter.getBook("java");

        // assert - then
        assertNull(response);
    }

}
