package com.baeldung.hexagonpattern.domain;

public enum LibraryMaterial {

	BOOKS, MUSIC, MOVIES;

	public String toString() {
		switch (this) {
		case BOOKS:
			return "Books";
		case MUSIC:
			return "Music";
		case MOVIES:
			return "Movies";
		}
		return null;
	}
}
