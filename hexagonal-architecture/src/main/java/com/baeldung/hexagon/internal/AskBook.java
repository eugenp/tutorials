package com.baeldung.hexagon.internal;

/**
 * Command object representing the user request.
 * i.e Book which Author
 *
 */
public class AskBook {
	private String author;

	public AskBook(String author) {
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}
}
