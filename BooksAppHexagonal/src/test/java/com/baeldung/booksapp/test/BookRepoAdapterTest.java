package com.baeldung.booksapp.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.baeldung.booksapp.adapter.BookRepoImpl;
import com.baeldung.booksapp.core.domain.Book;
import com.baeldung.booksapp.port.BookRepo;

public class BookRepoAdapterTest {

	private BookRepo adapter;
	
	@Before
	public void setup() {
		adapter = new BookRepoImpl();
	}
	
	@Test
	public void bookRepo_whenThereAreNoStoredBooks_gettingAllBooksGivesEmptyList() {
		List<Book> books = adapter.getAllBooks();
		
		assertTrue(books.size() == 0);
	}
	
	@Test
	public void bookRepo_addNewBooks() {
		List<Book> books = adapter.getAllBooks();	
		assertTrue(books.size() == 0);
		
		Book bookDTO = new Book();
		bookDTO.setAuthor("Author_Name");
		bookDTO.setId(1);
		bookDTO.setName("Book_Name");
		
		adapter.createBook(bookDTO);
		books = adapter.getAllBooks();
		assertTrue(books.size() == 1);
	}
}
