package com.baeldung.hexagonalarchitecture;

import java.util.List;

public interface BookRepository {
	
	List<Book> findAll();
	
	void save(Book book);
	
	Book findByBookId(Integer bookId);
	
	List <Book> findByGenre(BookGenre genre);	
}
