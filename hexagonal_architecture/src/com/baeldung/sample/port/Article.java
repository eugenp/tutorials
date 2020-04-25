package com.baeldung.sample.port;

public class Article {
	public Heading heading;
	public String content;
	String createdDate;
	
	public String getHeading() {
		return heading.toString();
	}
	
	public String getHeadingSize() {
		return heading.size;
	}
	
	public void setHeading(String headingTxt, String size) {
		this.heading.setText(headingTxt);
		this.heading.setSize(size);
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
	
	public void setText(String headingTxt) {
		this.text = headingTxt;
	}
}
