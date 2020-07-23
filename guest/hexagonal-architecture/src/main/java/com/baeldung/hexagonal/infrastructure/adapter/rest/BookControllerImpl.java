package com.baeldung.hexagonal.infrastructure.adapter.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.application.service.api.BookService;
import com.baeldung.hexagonal.domain.model.Book;

import lombok.Getter;
import lombok.Setter;

@RestController
@Getter
@Setter
@RequestMapping("/")
public class BookControllerImpl implements BookController {

	@Autowired
	private BookService bookService;

	@Override
	public ResponseEntity<List<Book>> getBooks() {
		return new ResponseEntity<List<Book>>(bookService.getBooks(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> addBook(Book book) {
		bookService.addBook(book);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> removeBook(Book book) {
		bookService.removeBook(book);
	        return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Book> getBookById(Integer bookId) throws Exception {
		return new ResponseEntity<Book>(bookService.getBookById(bookId), HttpStatus.OK);
	}

}
