package com.baeldung.hexagonal.core.domain;

import java.io.Serializable;
import java.util.Objects;

public class Book implements Serializable {

	private static final long serialVersionUID = -62541878540546665L;
	private String name;
	private String authorName;
	private String publisherName;

	public Book(String name, String authorName, String publisherName) {
		this.name = name;
		this.authorName = authorName;
		this.publisherName = publisherName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void PublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Book book = (Book) o;
		return name.equals(book.name) && authorName.equals(book.authorName) && publisherName.equals(book.publisherName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, authorName, publisherName);
	}
}
