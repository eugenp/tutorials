
package com.baeldung.hexagonal;

import java.util.List;

public interface StorageService {
	public void storeTranslation(TranslationRequest translation);
	public void storeTranslation(List<TranslationRequest> translation);
	public List<TranslationRequest> getTranslations(String user);
}