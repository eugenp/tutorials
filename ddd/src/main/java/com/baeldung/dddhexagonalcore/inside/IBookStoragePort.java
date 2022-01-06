package com.baeldung.dddhexagonalcore.inside;

import java.util.Map;

public interface IBookStoragePort {

	public void storeBook(String isbn, String title);
	
	public String getBookTitle(String isbn);
	
	public Map<String, String> getAllBooks();
}
