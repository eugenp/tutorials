package com.baeldung.hexagonalPattern.adapters;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonalPattern.core.domain.Book;
import com.baeldung.hexagonalPattern.web.LibraryRestUI;

@RestController
@RequestMapping("/library")
public class LibraryRestController implements LibraryRestUI {

    @Override
    @PostMapping("/insertBook")
    public int insertBook(Book book) {
	return 0;
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
