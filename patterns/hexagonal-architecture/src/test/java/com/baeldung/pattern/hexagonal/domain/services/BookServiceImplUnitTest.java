package com.baeldung.pattern.hexagonal.domain.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.pattern.hexagonal.domain.model.Book;
import com.baeldung.pattern.hexagonal.persistence.BookRepository;

class BookServiceImplUnitTest {
	private BookRepository bookRepository;
	private BookService bookService;
	private Book book;

	@BeforeEach
	void setUp() {
		bookRepository = mock(BookRepository.class);
		bookService = new BookServiceImpl(bookRepository);
		book = new Book();
		book.setAuthor("Spring Developer");
		book.setName("Spring for beginners");
		book.setPrice(new BigDecimal("12.90"));

	}

	@Test
	void bookEntry() {
		when(bookRepository.bookEntry(any(Book.class))).thenReturn(book.getName());

		String testBookName = bookService.bookEntry(book);
		assertEquals(book.getName(), testBookName);
	}

	@Test
	void getBook() {
		when(bookRepository.getBook("Spring for beginners")).thenReturn(book);

		Book testBook = bookService.getBook("Spring for beginners");
		assertEquals(testBook, book);
	}

}