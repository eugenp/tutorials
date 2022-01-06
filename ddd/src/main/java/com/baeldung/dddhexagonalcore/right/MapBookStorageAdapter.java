package com.baeldung.dddhexagonalcore.right;

import java.util.HashMap;
import java.util.Map;

import com.baeldung.dddhexagonalcore.inside.IBookStoragePort;

public class MapBookStorageAdapter implements IBookStoragePort {
	
	private Map<String, String> bookStore;
	
	public MapBookStorageAdapter() {

		bookStore = new HashMap<>();
	}

	public void storeBook(String isbn, String title) {
		
		bookStore.put(isbn, title);
	}

	public String getBookTitle(String isbn) {
		
		return bookStore.get(isbn);
	}

	public Map<String, String> getAllBooks() {
		
		Map<String, String> result = new HashMap<>();
		result.putAll(bookStore);
		
		return result;
	}
}
