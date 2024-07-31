package com.baeldung.exceptions.localization;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalizedExceptionUnitTest {

    private Locale originalDefaultLocale;

    @Before
    public void saveOriginalDefaultLocale() {
        originalDefaultLocale = Locale.getDefault();
    }

    @After
    public void restoreOriginalDefaultLocale() {
        Locale.setDefault(originalDefaultLocale);
    }

    @Test
    public void givenUsEnglishDefaultLocale_whenLocalizingMessage_thenMessageComesFromDefaultMessages() {
        Locale.setDefault(Locale.US);

        LocalizedException localizedException = new LocalizedException("message.exception");
        String usEnglishLocalizedExceptionMessage = localizedException.getLocalizedMessage();

        assertThat(usEnglishLocalizedExceptionMessage).isEqualTo("I am an exception.");
    }

    @Test
    public void givenFranceFrenchDefaultLocale_whenLocalizingMessage_thenMessageComesFromFrenchTranslationMessages() {
        Locale.setDefault(Locale.FRANCE);

        LocalizedException localizedException = new LocalizedException("message.exception");
        String franceFrenchLocalizedExceptionMessage = localizedException.getLocalizedMessage();

        assertThat(franceFrenchLocalizedExceptionMessage).isEqualTo("Je suis une exception.");
    }

    @Test
    public void givenUsEnglishProvidedLocale_whenLocalizingMessage_thenMessageComesFromDefaultMessage() {
        LocalizedException localizedException = new LocalizedException("message.exception", Locale.US);
        String usEnglishLocalizedExceptionMessage = localizedException.getLocalizedMessage();

        assertThat(usEnglishLocalizedExceptionMessage).isEqualTo("I am an exception.");
    }

    @Test
    public void givenFranceFrenchProvidedLocale_whenLocalizingMessage_thenMessageComesFromFrenchTranslationMessages() {
        LocalizedException localizedException = new LocalizedException("message.exception", Locale.FRANCE);
        String franceFrenchLocalizedExceptionMessage = localizedException.getLocalizedMessage();

        assertThat(franceFrenchLocalizedExceptionMessage).isEqualTo("Je suis une exception.");
    }

}
