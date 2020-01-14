package com.baeldung.bookRating.port;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.baeldung.bookRating.domain.Book;

public interface BookInbound {
	
	@GetMapping
	public List<Book> getBooks() ;
	
	@GetMapping("/{bookName}")
	public Integer getRatingByName(@PathVariable String bookName);
	
	@PostMapping
	public void addBook(@RequestBody Book book);
	
	
}
