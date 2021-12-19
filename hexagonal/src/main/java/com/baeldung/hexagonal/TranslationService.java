
package com.baeldung.hexagonal;

import java.util.List;

public interface TranslationService {
	public TranslationRequest translate(String user, String targetLanguage, String text, boolean isFormal);
	public TranslationRequest translate(String user, String targetLanguage, String text);
	public List<TranslationRequest> getTranslations(String user);
}
