package com.baeldung.regex.camelcasetowords;

import org.junit.jupiter.api.Test;

import static com.baeldung.regex.camelcasetowords.CamelCaseToWords.findWordsInMixedCase;
import static org.assertj.core.api.Assertions.assertThat;

class CamelCaseToWordsUnitTest {

    @Test
    void givenPlainStringWithNonLetters_thenFindsWords() {
        assertThat(findWordsInMixedCase("some words"))
          .containsExactly("some", "words");
    }

    @Test
    void givenWordsInCamelCase_thenFindsWords() {
        assertThat(findWordsInMixedCase("thisIsCamelCaseText"))
          .containsExactly("this", "Is", "Camel", "Case", "Text");
    }

    @Test
    void givenWordsInTitleCase_thenFindsWords() {
        assertThat(findWordsInMixedCase("ThisIsTitleCaseText"))
          .containsExactly("This", "Is", "Title", "Case", "Text");
    }

    @Test
    void givenWordsAcrossMultipleTexts_thenFindsWords() {
        assertThat(findWordsInMixedCase("ThisIsTitleCaseText --- andSoIsThis"))
          .containsExactly("This", "Is", "Title", "Case", "Text", "and", "So", "Is", "This");
    }

    @Test
    void givenCamelCaseHasASingleLetterWord_thenItCanBeSplit() {
        assertThat(findWordsInMixedCase("thisHasASingleLetterWord"))
          .containsExactly("this", "Has", "A", "Single", "Letter", "Word");
    }
}