package com.baeldung.hexagonal.infrastructure.repository.cassandra;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import com.baeldung.hexagonal.domain.BookStatus;
import com.baeldung.hexagonal.domain.Book;


@UserDefinedType
public class BookEntity {
	
	@PrimaryKey
	private String title;
	
	private UUID bookId;
	
	private String author;
	
	private String publisher;
	
	private BookStatus status;
	
	private String memberName;
	
	private UUID memberId;
	
	private Date issueDate;
	
	private Date returnDate;
	
	public BookEntity(final Book book) {
		this.title = book.getBookTitle();
		this.bookId = book.getBookId();
		this.author = book.getAuthor();
		this.publisher = book.getPublisher();
		this.status = book.getStatus();
		this.memberName = book.getMemberName();
		this.memberId = book.getMemberId();
		this.issueDate = book.getIssueDate();
		this.returnDate = book.getReturnDate();		
	}

	public Book toBook() {
		Book book = new Book(title, author, publisher);
		
		book.setIssueDate(issueDate);
		book.setReturnDate(returnDate);
		book.setMemberInfo(memberId, memberName);
		book.setStatus(status);
		
		return book;
	}
	
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
