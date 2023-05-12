package com.baeldung.fluentinterface.components;

public class HtmlHeader implements HtmlElement {

	private final Type type;
	private final String value;

	public HtmlHeader(Type type, String value) {
		this.type = type;
		this.value = value;
	}

	@Override
	public String html() {
		return String.format("<%s>%s</%s>", type.tag(), value, type.tag());
	}

	public enum Type {
		PRIMARY("h1"),
		SECONDARY("h2"),
		THIRD("h3"),
		FOURTH("h4");

		private final String tag;

		Type(String tag) {
			this.tag = tag;
		}

		public String tag() {
			return tag;
		}
	}
}
