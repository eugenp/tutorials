
package com.baeldung.hexagonal;

public interface TranslationAPI {
	public String translate(String srcLanguage, String targetLanguage, String text, boolean isFormal);
}