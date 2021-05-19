package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.core.domain.Book;
import com.baeldung.hexagonal.port.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class LibraryController {

	@Autowired
	private LibraryService libraryService;

	@PostMapping
	public void addBook(@RequestBody Book book) {
		libraryService.addBook(book);
	}

	@GetMapping("/{name}")
	public Book getBookDetailsByName(@PathVariable String name) {
		return libraryService.getBookDetailsByName(name);
	}

	@GetMapping
	public List<Book> getAllBookDetails() {
		return libraryService.getAllBookDetails();
	}
}