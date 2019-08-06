package org.baeldung.hexa.domain;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class BookUnitTest {

    @Test
    public void whenBookEquals_thenSuccess() {
        EqualsVerifier.forClass(Book.class);
    }

}
