package com.baeldung.hexagon.architecture.service;

import java.util.List;
import java.util.Optional;

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
		Optional<Book> bookOptional = bookRepository.findById(title);
		return bookOptional.isPresent() ? bookOptional.get() : null;
	}

	@Override
	public List<Book> getBooks() {
		return bookRepository.findAll();
	}

}