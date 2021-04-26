package com.baeldung.hexagonalPattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.baeldung.hexagonalPattern.core.domain.Book;
import com.baeldung.hexagonalPattern.core.impl.LibraryServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class HexagonalLibraryApplicationTest {

    @Mock
    private LibraryServiceImpl libService = org.mockito.Mockito.mock(LibraryServiceImpl.class);;
    Book bk = new Book();
    Book book;

    @Test
    public void when_InsertBook_then_returnInteger() {
	Mockito.when(libService.insertBook(bk)).thenReturn(1);
	int returnVal = libService.insertBook(bk);
	assertEquals(1, returnVal);
    }

    @Test
    public void when_SearchBook_then_returnBookObject() {
	Book returnBook = new Book();
	Mockito.lenient().when(libService.searchBook("Pride and Prejudice")).thenReturn(returnBook);
	assertNotNull(returnBook);
    }

    @Test
    public void when_getAllBooks_then_returnListofBookObjects() {
	List<Book> returnBookList = new ArrayList<Book>();
	returnBookList = libService.getAllBooks();
	assertNotNull(returnBookList);
    }
}
