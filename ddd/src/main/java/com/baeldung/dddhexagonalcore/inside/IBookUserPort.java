package com.baeldung.dddhexagonalcore.inside;

import java.util.Map;

public interface IBookUserPort {

	public void checkIsbn(String isbn) throws IllegalArgumentException;
	
	public void storeBookToDatabase(String isbn, String title) throws IllegalArgumentException;
	
	public String getBookFromDatabase(String isbn) throws IllegalArgumentException;
	
	public Map<String, String> getAllBooksFromDatabase();
}
