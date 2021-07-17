package com.baeldung.pattern.hexagonal.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.data.annotation.Id;

public class Book {
	@Id
	private String name;
	private BigDecimal price;
	private String author;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Book book = (Book) o;
		return name.equals(book.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

}
