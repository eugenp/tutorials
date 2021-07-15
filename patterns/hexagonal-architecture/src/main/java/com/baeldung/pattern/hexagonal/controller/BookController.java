package com.baeldung.pattern.hexagonal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.pattern.hexagonal.domain.model.Book;
import com.baeldung.pattern.hexagonal.domain.services.BookService;

@RestController
@RequestMapping("/books")
public class BookController {
	@Autowired
	BookService bookService;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String bookEntry(@RequestBody Book book) {
		return bookService.bookEntry(book);
	}

	@GetMapping(path = "/{name}")
	public Book getEmployee(@PathVariable("name") String name) {
		return bookService.getBook(name);
	}
}
