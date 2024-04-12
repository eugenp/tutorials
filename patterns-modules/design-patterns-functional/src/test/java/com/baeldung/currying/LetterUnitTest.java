package com.baeldung.currying;

import org.junit.Test;

import java.time.LocalDate;

import static com.baeldung.currying.Letter.LETTER_CREATOR;
import static com.baeldung.currying.Letter.SIMPLE_CURRIED_LETTER_CREATOR;
import static com.baeldung.currying.Letter.SIMPLE_LETTER_CREATOR;
import static org.junit.Assert.assertEquals;

public class LetterUnitTest {
    private static final String BODY = "BODY";
    private static final String SALUTATION = "SALUTATION";
    private static final String RETURNING_ADDRESS = "RETURNING ADDRESS";
    private static final String INSIDE_ADDRESS = "INSIDE ADDRESS";
    private static final LocalDate DATE_OF_LETTER = LocalDate.of(2013, 3, 1);
    private static final String CLOSING = "CLOSING";
    private static final Letter SIMPLE_LETTER = new Letter(SALUTATION, BODY);
    private static final Letter LETTER = new Letter(RETURNING_ADDRESS, INSIDE_ADDRESS, DATE_OF_LETTER, SALUTATION, BODY, CLOSING);

    @Test
    public void whenStaticCreateMethodIsCalled_thenaSimpleLetterIsCreated() {
        assertEquals(SIMPLE_LETTER, Letter.createLetter(SALUTATION, BODY));
    }

    @Test
    public void whenStaticBiFunctionIsCalled_thenaSimpleLetterIsCreated() {
        assertEquals(SIMPLE_LETTER, SIMPLE_LETTER_CREATOR.apply(SALUTATION, BODY));
    }

    @Test
    public void whenStaticSimpleCurriedFunctionIsCalled_thenaSimpleLetterIsCreated() {
        assertEquals(SIMPLE_LETTER, SIMPLE_CURRIED_LETTER_CREATOR.apply(SALUTATION).apply(BODY));
    }

    @Test
    public void whenStaticCurriedFunctionIsCalled_thenaLetterIsCreated() {
        assertEquals(LETTER, LETTER_CREATOR
            .apply(RETURNING_ADDRESS)
            .apply(CLOSING)
            .apply(DATE_OF_LETTER)
            .apply(INSIDE_ADDRESS)
            .apply(SALUTATION)
            .apply(BODY));
    }

    @Test
    public void whenStaticBuilderIsCalled_thenaLetterIsCreated() {
        assertEquals(LETTER, Letter.builder()
            .withReturnAddress(RETURNING_ADDRESS)
            .withClosing(CLOSING)
            .withDateOfLetter(DATE_OF_LETTER)
            .withInsideAddress(INSIDE_ADDRESS)
            .withSalutation(SALUTATION)
            .withBody(BODY));
    }
}