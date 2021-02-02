package com.baeldung.booksapp.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.baeldung.booksapp.core.domain.Book;

public interface BookRestUI {
	
	@PostMapping
	void createBook(@RequestBody Book book);
	

	@GetMapping("/{name}")
	public Book getBook(@PathVariable String name);

	@GetMapping
	public List<Book> listBook() ;

}
