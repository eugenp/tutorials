package com.baeldung.fluentinterface.components;

public class HorizontalLine implements HtmlElement {
	@Override
	public String html() {
		return "<hr>";
	}
}
