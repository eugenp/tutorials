package com.baeldung.exceptions.localization;

import org.junit.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class MessagesUnitTest {

    @Test
    public void givenUsEnglishLocale_whenRetrievingMessage_thenEnglishTranslationIsReturned() {
        String translatedMessage = Messages.getMessageForLocale("message.exception", Locale.US);

        assertThat(translatedMessage).isEqualTo("I am an exception.");
    }

    @Test
    public void givenFranceFrenchLocale_whenRetrievingMessage_thenFrenchTranslationIsReturned() {
        String translatedMessage = Messages.getMessageForLocale("message.exception", Locale.FRANCE);

        assertThat(translatedMessage).isEqualTo("Je suis une exception.");
    }

}
