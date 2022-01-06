package com.baeldung.dddhexagonalcore.left;

import java.util.Map;

import com.baeldung.dddhexagonalcore.inside.IBookUserPort;

public class ConsoleAdapter {

	private IBookUserPort logic;

	public ConsoleAdapter(IBookUserPort logic) {
		
		this.logic = logic;
	}
	
	public boolean checkIsbn(String isbn) {
		
		try {
			System.out.println("Checking ISBN number : " + isbn);
			logic.checkIsbn(isbn);
			System.out.println("ISBN is valid");
			return true;
			
		}catch (IllegalArgumentException e) {
			
			System.out.println("ISBN is not valid !");
			return false;
		}
	}
	
	public void store(String isbn, String title) {
		
		try {
			System.out.println("Storing book '" + title + "' with ISBN " + isbn);
			logic.storeBookToDatabase(isbn, title);
			
		} catch (IllegalArgumentException e) {
			
			System.out.println("An error occured : " + e.getMessage());
		}
	}
	
	public String retrieve(String isbn) {
		
		try {
			System.out.println("Retrieving book with ISBN " + isbn);
			return logic.getBookFromDatabase(isbn);
			
		} catch (IllegalArgumentException e) {
			
			System.out.println("An error occured : " + e.getMessage());
		}
		
		return null;
	}
	
	public Map<String, String> getAll() {
		
		return logic.getAllBooksFromDatabase();
	}
}
