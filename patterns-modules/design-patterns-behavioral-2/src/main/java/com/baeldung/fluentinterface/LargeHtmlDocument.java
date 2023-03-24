package com.baeldung.fluentinterface;

import com.baeldung.fluentinterface.components.HtmlElement;
import com.baeldung.fluentinterface.components.HtmlHeader;
import com.baeldung.fluentinterface.components.HtmlList;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

public class LargeHtmlDocument {
	private final String content;

	private LargeHtmlDocument(String html) {
		this.content = html;
	}

	public LargeHtmlDocument() {
		this("");
	}

	public String html() {
		return format("<html>%s</html>", content);
	}

	public LargeHtmlDocument head(HtmlElement head) {
		return new LargeHtmlDocument(format("%s <head>%s</head>", content, head.html()));
	}
	public LargeHtmlDocument body(HtmlElement body) {
		return new LargeHtmlDocument(format("%s <body>%s</body>", content, body.html()));
	}
	public LargeHtmlDocument footer(HtmlElement footer) {
		return new LargeHtmlDocument(format("%s <footer>%s</footer>", content, footer.html()));
	}

	private LargeHtmlDocument append(String html) {
		return new LargeHtmlDocument(format("%s %s", content, html));
	}

}