package org.baeldung.hexagonal.news;

/**
 * An article created in the app
 *
 */
public class Article {
	
	private String title;
	
	private String body;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
