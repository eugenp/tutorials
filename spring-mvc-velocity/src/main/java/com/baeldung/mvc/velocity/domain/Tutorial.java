package com.baeldung.mvc.velocity.domain;

public class Tutorial {

	private Integer tutId;
	private String title;
	private String description;
	private String author;

	public Tutorial() {
		super();
	}

	public Tutorial(Integer tutId, String title, String description, String author) {
		super();
		this.tutId = tutId;
		this.title = title;
		this.description = description;
		this.author = author;
	}

	public Integer getTutId() {
		return tutId;
	}

	public void setTutId(Integer tutId) {
		this.tutId = tutId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}
