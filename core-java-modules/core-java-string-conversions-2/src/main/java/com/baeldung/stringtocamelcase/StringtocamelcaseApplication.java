package com.baeldung.stringtocamelcase;

import com.google.common.base.CaseFormat;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.BreakIterator;
import org.apache.commons.text.CaseUtils;
import org.apache.commons.text.WordUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringtocamelcaseApplication {

	public static void main(String[] args) {

	}


	//Core Java Solutions
	public static String toCamelCaseByIteration(String text, char delimiter) {
		if (text == null || text.isEmpty()) return text;
		boolean shouldConvertNextCharToLower = false;
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < text.length(); i++) {
			char currentChar = text.charAt(i);

			if (currentChar == delimiter) {
				shouldConvertNextCharToLower = false;
			} else if (shouldConvertNextCharToLower) {
				builder.append(Character.toLowerCase(currentChar));
			} else {
				//Bingo!!! We,ve seen a Word Breaker
				//This also caters for converting the first character to Uppercase
				builder.append(Character.toUpperCase(currentChar));
				shouldConvertNextCharToLower = true;
			}

		}

		return builder.toString();
	}

	public static String toCamelCaseBySplitting(String text, String delimiter) {
		if (text == null || text.isEmpty()) return text;
		String [] words = text.split(delimiter);

		StringBuilder builder = new StringBuilder();

		for (String word : words) {
			//Convert the first character to Uppercase and others to lowercase
			// e.g sTRING =====> String
			word = word.isEmpty() ? word : Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
			builder.append(word);
		}

		return builder.toString();
	}

	public static String toCamelCaseBySplittingUsingStreams(String text, String delimiter) {
		if (text == null || text.isEmpty()) {
			return text;
		}

		return Arrays.stream(text.split(delimiter))
				.map(word -> word.isEmpty() ? word
						: Character.toUpperCase(word.charAt(0))
						+ word.substring(1).toLowerCase())
				.collect(Collectors.joining(""));
	}

	//Third-Party Libraries
	public static String toCamelCaseUsingApacheCommonsText(String text, char delimiter) {
		return CaseUtils.toCamelCase(text, true, delimiter);
	}

	public static String toCamelCaseUsingICU4J(String text, String delimiter) {
		if (text == null || text.isEmpty()) return text;

		return UCharacter.toTitleCase(text, BreakIterator.getTitleInstance()).replaceAll(delimiter, "");
	}

	public static String toCamelCaseUsingGuava(String text, String delimiter) {
		String toUpperUnderscore = text.toUpperCase().replaceAll(delimiter, "_");
		return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, toUpperUnderscore).replaceAll("_", "");
	}

	public static String toCamelCaseUsingApacheCommons(String text, char delimiter) {
		return WordUtils.capitalizeFully(text, delimiter).replaceAll(String.valueOf(delimiter), "");
	}

}
