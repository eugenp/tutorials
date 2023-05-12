package com.baeldung.lombok;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ThankingServiceIntegrationTest {

    private final static String TRANSLATED = "TRANSLATED";

    @Test
    public void thankWithTranslatedMessage() {
        Translator translator = mock(Translator.class);
        when(translator.translate("thank you")).thenReturn(TRANSLATED);
        ThankingService thankingService = new ThankingService(translator);
        assertEquals(TRANSLATED, thankingService.thank());
    }
}