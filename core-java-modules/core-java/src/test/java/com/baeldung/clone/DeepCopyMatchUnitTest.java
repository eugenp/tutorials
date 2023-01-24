package com.baeldung.clone;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeepCopyMatchUnitTest {

    @Test
    void givenMatchHasDeepCopyImplementation_shouldHaveDifferentMatchDifferentPlayersDetail() throws CloneNotSupportedException {
        DeepMatch deepMatch = new DeepMatch(ShallowMatch.Type.TENNIS, OffsetDateTime.MIN, OffsetDateTime.MAX, Integer.MAX_VALUE, new PlayersDetail("Rafael", "Novak"));
        DeepMatch deepCopyMatch = (DeepMatch) deepMatch.clone();

        assertNotNull(deepCopyMatch);
        assertFalse(deepMatch == deepCopyMatch);
        assertTrue(deepMatch.getType() == deepMatch.getType());
        assertFalse(deepMatch.getPlayersDetail() == deepCopyMatch.getPlayersDetail());
    }

}