package com.baeldung.bookRating.domain;

import java.io.Serializable;

public class Book implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String author;
	
	private Integer rating;
	
	
	public Book(String name) {
		super();
		this.name = name;
	}

	public Book(String name, String author, Integer rating) {
		super();
		this.name = name;
		this.author = author;
		this.rating = rating;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
