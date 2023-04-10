package com.baeldung.fluentinterface;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

public class HtmlDocument {
	private final String content;

	private HtmlDocument(String html) {
		this.content = html;
	}

	public HtmlDocument() {
		this("");
	}

	public String html() {
		return String.format("<html>%s</html>", content);
	}

	public HtmlDocument header(String header) {
		return new HtmlDocument(String.format("%s <h1>%s</h1>", content, header));
	}

	public HtmlDocument secondaryHeader(String header) {
		return new HtmlDocument(String.format("%s <h2>%s</h2>", content, header));
	}

	public HtmlDocument paragraph(String paragraph) {
		return new HtmlDocument(String.format("%s <p>%s</p>", content, paragraph));
	}

	public HtmlDocument horizontalLine() {
		return new HtmlDocument(String.format("%s <hr>", content));
	}

	public HtmlDocument orderedList(String... items) {
		String listItems = stream(items).map(el -> String.format("<li>%s</li>", el)).collect(joining());
		return new HtmlDocument(String.format("%s <ol>%s</ol>", content, listItems));
	}

	public HtmlDocument unorderedList(String... items) {
		String listItems = stream(items).map(el -> String.format("<li>%s</li>", el)).collect(joining());
		return new HtmlDocument(String.format("%s <ul>%s</ul>", content, listItems));
	}
}