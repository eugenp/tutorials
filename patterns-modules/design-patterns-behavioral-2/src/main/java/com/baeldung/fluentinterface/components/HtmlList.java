package com.baeldung.fluentinterface.components;

import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

public class HtmlList implements HtmlElement {

	private final Type type;
	private final List<String> items;

	public HtmlList(Type type, String... items) {
		this.type = type;
		this.items = Arrays.asList(items);
	}

	@Override
	public String html() {
		String listItems = items.stream().map(el -> format("<li>%s</li>", el)).collect(joining());
		return String.format("<%s>%s</%s>", type.tag(), listItems, type.tag());
	}

	public enum Type {
		ORDERED("ol"),
		UNORDERED("ul");

		private final String tag;

		Type(String tag) {
			this.tag = tag;
		}

		public String tag() {
			return tag;
		}
	}
}
