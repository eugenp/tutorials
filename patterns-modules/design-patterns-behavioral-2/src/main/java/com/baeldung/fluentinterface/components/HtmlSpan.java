package com.baeldung.fluentinterface.components;

public class HtmlSpan implements HtmlElement {

	private final String content;

	public HtmlSpan(String content) {
		this.content = content;
	}

	public HtmlSpan() {
		this.content = "";
	}

	public HtmlSpan append(HtmlElement element) {
		return new HtmlSpan(content + element.html());
	}

	public HtmlSpan paragraph(String text) {
		return new HtmlSpan(content + text);
	}

	@Override
	public String html() {
		return String.format("<span>%s</span>", content);
	}
}
