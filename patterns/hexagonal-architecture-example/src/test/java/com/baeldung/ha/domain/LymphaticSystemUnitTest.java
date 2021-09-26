package com.baeldung.ha.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LymphaticSystemUnitTest {

    @Test
    void givenInvalidAntigen_whenProduce_thenThrowException() {
        // Null antigen
        assertThrows(InvalidAntigenException.class, () -> {
            LymphaticSystem.produce(null);
        });

        // Lower limit
        assertThrows(InvalidAntigenException.class, () -> {
            LymphaticSystem.produce(new Antigen(-1));
        });

        // Lesser than lower limit
        assertThrows(InvalidAntigenException.class, () -> {
            LymphaticSystem.produce(new Antigen(-5));
        });

        // Upper limit
        assertThrows(InvalidAntigenException.class, () -> {
            LymphaticSystem.produce(new Antigen(LymphaticSystem.MAX_ANTIGEN_VALUE));
        });

        // Greater than upper limit
        assertThrows(InvalidAntigenException.class, () -> {
            LymphaticSystem.produce(new Antigen(LymphaticSystem.MAX_ANTIGEN_VALUE + 3));
        });
    }

    @Test
    void givenValidAntigen_whenProduce_thenNoException() throws InvalidAntigenException {
        Antigen antigen = new Antigen(LymphaticSystem.MAX_ANTIGEN_VALUE - 1);
        Antibody antibody = LymphaticSystem.produce(antigen);
        assertNotNull(antibody);
        assertEquals(antigen, antibody.getAntigen());
        assertTrue(antibody.getEffort() > 0);
    }
}
