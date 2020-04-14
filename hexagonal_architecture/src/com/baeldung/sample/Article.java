package com.baeldung.sample;

public class Article {
	Heading heading;
	String content;
	String createdDate;
	
	public Heading getHeading() {
		return heading;
	}
	
	public void setHeading(Heading heading) {
		this.heading = heading;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public String getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
}

class Heading {
	public String size;
	public String text;

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
}
