package com.baeldung.hexagonal.infrastructure.adapter.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.domain.model.Book;
import com.baeldung.hexagonal.domain.port.BookPersistencePort;

@Service
@Qualifier("bookInMemoryAdapter")
public class BookInMemoryAdapter implements BookPersistencePort {

	
	 private static final Map<Integer, Book> bookMap = new HashMap<Integer, Book>(0);

	@Override
	public void addBook(Book book) {
		bookMap.put(book.getBookId(), book);
		
	}

	@Override
	public void removeBook(Book book) {
		bookMap.remove(book.getBookId());
		
	}

	@Override
	public List<Book> getBooks() {
		return new ArrayList<Book>(bookMap.values());
	}

	@Override
	public Book getBookById(Integer bookId) {
		 return bookMap.get(bookId);
	}

}
