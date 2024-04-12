package com.baeldung.fluentinterface.components;

public class HtmlDiv implements HtmlElement {

	private final String content;

	public HtmlDiv(String content) {
		this.content = content;
	}

	public HtmlDiv() {
		this.content = "";
	}

	public HtmlDiv append(HtmlElement element) {
		return new HtmlDiv(content + element.html());
	}
	public HtmlDiv text(String text) {
		return new HtmlDiv(content + text);
	}
	public HtmlDiv paragraph(String text) {
		return new HtmlDiv(content + text);
	}

	@Override
	public String html() {
		return String.format("<div>%s</div>", content);
	}
}
