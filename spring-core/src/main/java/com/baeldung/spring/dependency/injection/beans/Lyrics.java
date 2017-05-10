package com.baeldung.spring.dependency.injection.beans;

import org.springframework.beans.factory.annotation.Autowired;

public class Lyrics {

	private String id;
	private Language language;

	public Lyrics() {
	}

	// costructor-based dependency injection of Language
	// using annotation
	@Autowired
	public Lyrics(Language language) {
		this.language = language;
	}

	public Language getLanguage() {
		return language;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void printLyricsId(){
		System.out.println("Lyrics Id : "+id);
	}
}
