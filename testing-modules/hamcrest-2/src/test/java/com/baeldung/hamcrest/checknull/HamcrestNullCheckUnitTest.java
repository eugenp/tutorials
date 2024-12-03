package com.baeldung.hamcrest.checknull;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class HamcrestNullCheckUnitTest {

    @Test
    void whenUsingNullValue_thenCorrect() {
        String theNull = null;
        assertThat(theNull, nullValue());

        String theNotNull = "I am a good String";
        assertThat(theNotNull, notNullValue());
        assertThat(theNotNull, not(nullValue()));

    }

    @Test
    void whenUsingJunitAssertion_thenCorrect() {
        String theNull = null;
        assertNull(theNull);

        String theNotNull = "I am a good String";
        assertNotNull(theNotNull);
    }
}