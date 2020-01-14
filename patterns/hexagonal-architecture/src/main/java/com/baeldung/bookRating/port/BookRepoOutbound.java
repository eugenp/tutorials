package com.baeldung.bookRating.port;

import java.util.List;
import com.baeldung.bookRating.domain.Book;

public interface BookRepoOutbound {
	
	public List<Book> getBooks();
	
	public Integer getRatingByName(String bookName);
	
	public void addBook(Book book);

}
