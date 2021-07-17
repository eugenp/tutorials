package com.baeldung.pattern.hexagonal.domain.services;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.baeldung.pattern.hexagonal.domain.model.Book;
import com.baeldung.pattern.hexagonal.persistence.BookRepository;

@Service
public class BookServiceImpl implements BookService {

	private BookRepository bookRepository;

	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public Book getBook(String name) {
		Optional<Book> book = bookRepository.getBook(name);

		if (book.isPresent()) {
			return book.get();
		} else {
			Book noBook = new Book();
			noBook.setName("No Book Found");
			noBook.setAuthor("N/A");
			noBook.setPrice(new BigDecimal("0.00"));
			return noBook;
		}
	}

	@Override
	public String bookEntry(Book book) {
		return bookRepository.bookEntry(book).getName();
	}

}
