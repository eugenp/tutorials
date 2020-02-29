package com.baeldung.hexagonalarchitecture;

public class Book {
	
	private Integer id;
	private String name;	
	private BookGenre genre;
	private Integer numberOfReaders;

	public Book(Integer id, String name, BookGenre genre, Integer numberOfReaders) {
		this.id = id;
		this.name = name;
		this.genre = genre;
		this.numberOfReaders = numberOfReaders;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
