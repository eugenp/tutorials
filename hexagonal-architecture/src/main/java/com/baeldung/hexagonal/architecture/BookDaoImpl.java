package com.baeldung.hexagonal.architecture;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookDaoImpl implements BooksDao {
	private List<Book> bookList = new ArrayList<>();

	@PostConstruct
	public void initialize() {
		bookList.add(new Book(1, "Becoming"));
		bookList.add(new Book(2, "The Wonky Donkey"));
		bookList.add(new Book(3, "12 Rules for Life"));
		bookList.add(new Book(4, "Diary of a Wimpy Kid"));
	}

	@Override
	public List<Book> findAllBooks() {
		return bookList;
	}
}