package com.baeldung.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baeldung.domain.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class LibraryService {

	private static ObjectWriter mapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
	private static Map<String, Book> library = new HashMap<String,Book>();

	public static String view() throws JsonProcessingException {
		List<Book> books = new ArrayList<Book>();
		library.forEach((key, value) -> {
		    books.add(value);
		});
		return mapper.writeValueAsString(books);
	}
	
	public static String view(String title) throws JsonProcessingException {
		return mapper.writeValueAsString(library.get(title));
	}
	
	public static String add(Book book) throws JsonProcessingException {
		library.put(book.getTitle(), book);
		return mapper.writeValueAsString(book);
	}
	
	public static String delete(String title) throws JsonProcessingException {
		Book deletedBook = library.remove(title);
		return mapper.writeValueAsString(deletedBook);
	}
	
}
