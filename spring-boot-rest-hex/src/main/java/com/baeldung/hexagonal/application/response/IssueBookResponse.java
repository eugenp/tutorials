package com.baeldung.hexagonal.application.response;

import java.util.UUID;

public class IssueBookResponse {
	
	private final UUID bookId;
	
	public IssueBookResponse(final UUID bookId) {
		this.bookId = bookId;
	}
	
	public UUID getId() {
		return bookId;
	}

}
