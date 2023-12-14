package com.baeldung.cactoos;

import java.io.IOException;

import org.cactoos.text.FormattedText;
import org.cactoos.text.IsBlank;
import org.cactoos.text.Lowered;
import org.cactoos.text.TextOf;
import org.cactoos.text.Upper;

public class CactoosStringUtils {

	public String createString() throws IOException {
		String testString = new TextOf("Test String").asString();
		return testString;
	}

	public String createdFormattedString(String stringToFormat) throws IOException {
		String formattedString = new FormattedText("Hello %s", stringToFormat).asString();
		return formattedString;
	}

	public String toLowerCase(String testString) throws IOException {
		String lowerCaseString = new Lowered(new TextOf(testString)).asString();
		return lowerCaseString;
	}

	public String toUpperCase(String testString) throws Exception {
		String upperCaseString = new Upper(new TextOf(testString)).asString();
		return upperCaseString;
	}

	public boolean isBlank(String testString) throws Exception {
		return new IsBlank(new TextOf(testString)) != null;
	}

}
