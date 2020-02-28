package com.baeldung.hexagonalarchitecture;

public class Book {
	
	private Integer bookId;
	private String bookName;	
	private BookGenre genre;
	private Integer numberOfReaders;

	public Book(Integer bookId, String bookName, 
	  BookGenre genre, Integer numberOfReaders) {
		this.bookId = bookId;
		this.bookName = bookName;
		this.genre = genre;
		this.numberOfReaders = numberOfReaders;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public BookGenre getGenre() {
		return genre;
	}

	public void setGenre(BookGenre genre) {
		this.genre = genre;
	}

	public Integer getNumberOfReaders() {
		return numberOfReaders;
	}

	public void setNumberOfReaders(Integer numberOfReaders) {
		this.numberOfReaders = numberOfReaders;
	}
}
