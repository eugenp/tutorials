package com.baeldung.hexagonal.infrastructure.adapter.rest;



import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.baeldung.hexagonal.application.service.api.BookService;
import com.baeldung.hexagonal.domain.model.Book;
import com.baeldung.hexagonal.infrastructure.adapter.rest.BookControllerImpl;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerImplTest extends TestCase {

	
	@Mock
	private BookService bookService;

	@InjectMocks
	private BookControllerImpl bookControllerImpl;

	private Book book;

	@Before
	public void setup() throws Exception {
		book = Book.builder().author("Auth").bookId(1).description("Testing Book").build();
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void testGetBooks() throws Exception {
		ArrayList<Book> bookList = new ArrayList<Book>();
		bookList.add(book);

		when(bookService.getBooks()).thenReturn(bookList);
		(assertThat(bookControllerImpl.getBooks())).isNotNull();
	}

}
