package com.baeldung.hexagonal.application.service.adapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.application.service.api.BookService;
import com.baeldung.hexagonal.domain.model.Book;
import com.baeldung.hexagonal.domain.port.BookPersistencePort;

@Service
public class BookServiceAdapter implements BookService {

	
	@Autowired
	@Qualifier("bookInMemoryAdapter")
	private BookPersistencePort port;

	@Override
	public void addBook(Book book) {
		port.addBook(book);

	}

	@Override
	public void removeBook(Book book) {
		port.removeBook(book);

	}

	@Override
	public List<Book> getBooks() {

		return port.getBooks();
	}

	@Override
	public Book getBookById(Integer bookId) throws Exception {

		return port.getBookById(bookId);
	}
}
