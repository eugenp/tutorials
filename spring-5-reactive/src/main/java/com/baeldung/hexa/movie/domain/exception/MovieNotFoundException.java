package com.baeldung.hexa.movie.domain.exception;

public class MovieNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2289163557509876597L;

	public MovieNotFoundException(String title) {
		super("Movie [" + title + "] not found");
	}	
}
