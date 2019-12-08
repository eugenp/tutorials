package com.baeldung.hexagon.adapter;

import com.baeldung.hexagon.port.IGetBook;

/**
 * Adapter as Library repository 
 * 
 */
public class Library implements IGetBook {
	public String[] getBook(String author) {
		if ("Charles Dickens".equalsIgnoreCase(author)) {
			return new String[] {
					"A Christmas Carol",
					"A Tale Of Two Cities"
					};

		} else if("William Shakespeare".equalsIgnoreCase(author)){ 
			return new String[] {
					"Romeo Juliet",
					"Hamlet",
					"Macbeth" };
		} else {
			return new String[] {};
		}
	}
}