package com.baeldung.hexagonalPattern.core.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonalPattern.adapters.LibraryRepoImpl;
import com.baeldung.hexagonalPattern.core.domain.Book;
import com.baeldung.hexagonalPattern.ports.LibraryService;

@Service
public class LibraryServiceImpl implements LibraryService {

    @Autowired
    private LibraryRepoImpl bookRepo = new LibraryRepoImpl();

    @Override
    public int insertBook(Book book) {
	bookRepo.insertBook(book);
	return 1;
    }

    @Override
    public Book searchBook(String name) {
	return bookRepo.searchBook(name);
    }

    @Override
    public List<Book> getAllBooks() {
	return bookRepo.getAllBooks();
    }
}
