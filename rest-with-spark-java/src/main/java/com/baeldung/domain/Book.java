package com.baeldung.domain;

public class Book {

	private String title;
	private String author;
	private String publisher;

	public Book() {}
	
	public Book(String title, String author, String publisher) {
		super();
		this.title = title;
		this.author = author;
		this.publisher = publisher;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	protected boolean canEqual(Object other) {
		return other instanceof Book;
	}

	@Override 
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof Book)) return false;
		Book other = (Book) o;
		if (!other.canEqual((Object)this)) return false;
		if (this.getTitle() == null ? other.getTitle() != null : !this.getTitle().equals(other.getTitle())) return false;
		return true;
	}
	
}
