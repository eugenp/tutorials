package com.baeldung.hexagon.architecture.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagon.architecture.domain.entity.Book;
import com.baeldung.hexagon.architecture.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	BookService bookService;

	@PostMapping
	public void addBook(@RequestBody Book book) {
		bookService.addBook(book);
	}

	@GetMapping("/{title}")
	public Book getBook(@PathVariable String title) {
		return bookService.getBook(title);
	}

	@GetMapping
	public List<Book> getBooks() {
		return bookService.getBooks();
	}

}
