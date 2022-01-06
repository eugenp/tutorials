package com.baeldung.dddhexagonalcore.inside;

import java.util.Map;

public class BookApplication implements IBookUserPort {

	private IBookStoragePort storage;

	public BookApplication(IBookStoragePort storage) {
				
		this.storage = storage;
	}
	
	public void checkIsbn(String isbn) throws IllegalArgumentException {
		
		if(isbn == null || (isbn.length() != 10 && isbn.length() != 13))
			throw new IllegalArgumentException("ISBN is not valid");
	}
	
	public void storeBookToDatabase(String isbn, String title) throws IllegalArgumentException {
		
		checkIsbn(isbn);
		storage.storeBook(isbn, title);
	}

	public String getBookFromDatabase(String isbn) throws IllegalArgumentException {
		
		checkIsbn(isbn);
		return storage.getBookTitle(isbn);
	}

	public Map<String, String> getAllBooksFromDatabase() {
		
		return storage.getAllBooks();
	}	
}
