package com.baeldung.pattern.hexagonal.domain.services;

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
		return bookRepository.getBook(name);
	}

	@Override
	public String bookEntry(Book book) {
		return bookRepository.bookEntry(book);
	}

}
