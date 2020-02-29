package com.baeldung.hexagonalarchitecture;

import java.util.List;

public interface BookRepository {
	
	List<Book> findAll();
	
	void save(Book book);
	
	Book findById(Integer id);
	
	List <Book> findByGenre(BookGenre genre);	
}
