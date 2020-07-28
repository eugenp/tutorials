package com.baeldung.hexagonal.domain;

import java.util.List;
import java.util.Calendar;
import java.util.UUID;

public class Library {
	private List<Book> bookList;
	
	private final int num_of_days = 14;
	
	private final UUID blank_UUID = new UUID(0, 0);
	
	private final UUID error_UUID = new UUID(0, -1);
	
	public BookStatus checkBookAvailability(String title) {
		Book bookEntry = getBookEntry(title);
		
		return bookEntry.getStatus();
	}
	
	public UUID issueBook(String title,
			UUID memberId,
			String memberName) {
		Book bookEntry = getBookEntry(title);
		
		if (bookEntry.getStatus() == BookStatus.ISSUED) {
			return error_UUID;
		}
		
		bookEntry.setStatus(BookStatus.ISSUED);
		bookEntry.setMemberInfo(memberId, memberName);
		
		Calendar cal = Calendar.getInstance();
		
		bookEntry.setIssueDate(cal.getTime());
		
		cal.add(Calendar.DAY_OF_YEAR, num_of_days);
		
		bookEntry.setReturnDate(cal.getTime());
		
		return bookEntry.getBookId();	
	}

	public void returnBook(String title) {
		Book bookEntry = getBookEntry(title);

		bookEntry.setStatus(BookStatus.AVAILABLE);
		bookEntry.setMemberInfo(blank_UUID, "");	
	}
	
	public Book getBookEntry(String title) {
		Book bookEntry = bookList.stream()				
				.filter(book -> book.getBookTitle()
						.equals(title))
				.findFirst()
				.orElseThrow(() -> new LibraryException(
						"Book title: " + title + " does not exist!"));
		
		return bookEntry;
	}
}
