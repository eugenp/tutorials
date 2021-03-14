package com.baeldung.hexagonalPattern.adapter;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonalPattern.core.domain.Book;
import com.baeldung.hexagonalPattern.web.LibraryRestUI;

@RestController
public class LibraryRestController implements LibraryRestUI {

	@Override
	@RequestMapping("/library")
	public void insertBook(Book book) {
		// TODO Auto-generated method stub

	}

	@Override
	@GetMapping("/searchBook")
	public Book searchBook(@PathVariable String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@GetMapping("/listBooks")
	public List<Book> listAllBooks() {
		// TODO Auto-generated method stub
		return null;
	}

}
