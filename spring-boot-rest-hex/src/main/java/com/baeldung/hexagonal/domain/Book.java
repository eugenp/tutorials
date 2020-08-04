package com.baeldung.hexagonal.domain;

import java.util.Date;
import java.util.UUID;

public class Book {
	
	private UUID bookId;
	
	private String title;
	
	private String author;
	
	private String publisher;
	
	private BookStatus status;
	
	private String memberName;
	
	private UUID memberId;
	
	private Date issueDate;
	
	private Date returnDate;
	
	public Book(String title,
			String author,
			String publisher) {
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.bookId = UUID.randomUUID();
		this.status = BookStatus.AVAILABLE;
	}
	
	// Getters and Setters
	public String getBookTitle() {
		return title;
	}
	
	public String getAuthor() {
		return author;	
	}
	
	public String getPublisher() {
		return publisher;
	}
	
	public UUID getBookId() {
		return bookId;
	}
	
	public void setStatus(BookStatus status) {
		this.status = status;
	}
	
	public BookStatus getStatus() {
		return status;
	}
	
	public void setMemberInfo(UUID memberId,
			String memberName) {
		this.memberId = memberId;
		this.memberName = memberName;
	}

	public UUID getMemberId() {
		return memberId;
	}
	
	public String getMemberName() {
		return memberName;
	}
	
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate; 
	}
	
	public Date getIssueDate() {
		return issueDate;
	}
	
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	
	public Date getReturnDate() {
		return returnDate;
	}
}
