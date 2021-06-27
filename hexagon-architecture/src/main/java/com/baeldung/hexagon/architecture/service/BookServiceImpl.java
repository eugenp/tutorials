package com.baeldung.hexagon.architecture.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.hexagon.architecture.domain.entity.Book;
import com.baeldung.hexagon.architecture.domain.entity.repository.BookRepository;

public class BookServiceImpl implements BookService {

	@Autowired
	BookRepository bookRepository;

	@Override
	public void addBook(Book book) {

		bookRepository.save(book);
	}

	@Override
	public Book getBook(String title) {
		List<Book> bookList = bookRepository.findByName(title);
		return bookList.get(0);
	}

	@Override
	public List<Book> getBooks() {
		return bookRepository.findAll();
	}

}