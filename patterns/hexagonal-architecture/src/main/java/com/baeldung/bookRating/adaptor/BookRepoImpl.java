package com.baeldung.bookRating.adaptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.baeldung.bookRating.domain.Book;
import com.baeldung.bookRating.port.BookRepoOutbound;

@Repository
public class BookRepoImpl implements BookRepoOutbound{
	
	private Map<String,Book> bookDatastore = new HashMap<String,Book>();

	@Override
	public List<Book> getBooks() {		
		return bookDatastore.values().stream().collect(Collectors.toList());
	}

	@Override
	public Integer getRatingByName(String bookName) {
		return bookDatastore.get(bookName).getRating();
	}

	@Override
	public void addBook(Book book) {
		bookDatastore.put(book.getName(), book);
	}

}
