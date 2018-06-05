package org.baeldung.web.listbindingexample;

import org.baeldung.persistence.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BooksCreationDto {

	private List<Book> books;

	public BooksCreationDto() {
		this.books = new ArrayList<>();
	}

	public BooksCreationDto(List<Book> books) {
		this.books = books;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public void addBook(Book book) {
		this.books.add(book);
	}
}
