package com.baeldung.hexagonal;

import org.junit.Assert;
import org.junit.Test;


public class HexagonalUnitTest {
	private TranslationService translationService;

    public HexagonalUnitTest(TranslationService translationService) {
        this.translationService = translationService;
    }

    @Test
    public void testTranslation() {
		Assert.assertEquals("livre", translationService.translate("*", "fr", "Book").getTranslatedText().toLowerCase());
    }

	
    
	
}