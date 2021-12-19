
package io.agh.hexagon;

import java.util.List;


interface TranslationService {
	public TranslationRequest translate(String user, String targetLanguage, String text, boolean isFormal);
	public TranslationRequest translate(String user, String targetLanguage, String text);
	public List<TranslationRequest> getTranslations(String user);
}
