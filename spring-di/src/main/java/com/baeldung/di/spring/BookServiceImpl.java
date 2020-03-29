package com.baeldung.di.spring;

import org.springframework.beans.factory.annotation.Autowired;

public class BookServiceImpl implements BookService {

	@Autowired(required = false)
	private AuthorService authorService;

}
