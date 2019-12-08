package com.baeldung.hexagon.internal;

import java.util.Objects;

public class Book {
	private String[] verses;

	/**
	 * Constructs a poem with verses by splitting the specified text using 
	 * newline characters.
	 * 
	 * @param text the text of the poem
	 */
	public Book(String text) {
		Objects.requireNonNull(text);
		this.verses = text.split("\\r?\\n");
	}

	/**
	 * Returns the individual verses of the poem.
	 * 
	 * @return the verses
	 */
	public String[] getVerses() {
		return verses;
	}
	
	@Override
	public String toString() {
		final String newline = System.getProperty("line.separator");
		return String.join(newline, verses);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((verses == null) ? 0 : verses.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (verses == null) {
			if (other.verses != null)
				return false;
		} else if (!verses.equals(other.verses))
			return false;
		return true;
	}
}
