package com.baeldung.bookRating.adaptor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.bookRating.domain.Book;
import com.baeldung.bookRating.port.BookInbound;
import com.baeldung.bookRating.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController implements BookInbound{

	@Autowired
	private BookService bookService;
	
	@Override
	public List<Book> getBooks() {
		return bookService.getBooks();
	}

	@Override
	public Integer getRatingByName(String bookName) {
		return bookService.getRatingByName(bookName);
	}

	@Override
	public void addBook(Book book) {
		bookService.addBook(book);
	}

}
