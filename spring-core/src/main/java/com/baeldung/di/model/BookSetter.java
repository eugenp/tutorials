package com.baeldung.di.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookSetter {

	private Author author;

	@Autowired
	public void setAuthor(Author author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return String.format("Author: %s", author);
	}

}
