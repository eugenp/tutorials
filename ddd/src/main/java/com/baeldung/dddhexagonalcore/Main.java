package com.baeldung.dddhexagonalcore;

import com.baeldung.dddhexagonalcore.inside.BookApplication;
import com.baeldung.dddhexagonalcore.inside.IBookStoragePort;
import com.baeldung.dddhexagonalcore.inside.IBookUserPort;
import com.baeldung.dddhexagonalcore.left.ConsoleAdapter;
import com.baeldung.dddhexagonalcore.right.MapBookStorageAdapter;

public class Main {

	public static void main(String[] args) {
		
		IBookStoragePort storage = new MapBookStorageAdapter();

		IBookUserPort hexagon = new BookApplication(storage);
		
		ConsoleAdapter adapter = new ConsoleAdapter(hexagon);
		
		adapter.checkIsbn("000121213");
		
		adapter.store("0123456789", "Book 1");
		adapter.store("9876543210", "Book 2");
		
		System.out.println("Book retrieved from database : " + adapter.retrieve("0123456789"));
		
		System.out.println("Retrieving all books :");
		adapter.getAll().forEach((isbn, title) -> System.out.println("Retrieved book '" + title + "' with ISBN " + isbn));
	}

}
