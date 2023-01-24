package com.baeldung.clone;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShallowMatchUnitTest {

    @Test
    void givenMatchHasShallowCopyImplementation_shouldHaveDifferentMatchSamePlayersDetailObject() throws CloneNotSupportedException {
        ShallowMatch shallowMatch = new ShallowMatch(ShallowMatch.Type.TENNIS, OffsetDateTime.MIN, OffsetDateTime.MAX, Integer.MAX_VALUE, new PlayersDetail("Rafael", "Novak"));
        ShallowMatch shallowCopyMatch = (ShallowMatch) shallowMatch.clone();

        assertNotNull(shallowCopyMatch);
        assertFalse(shallowMatch == shallowCopyMatch);
        assertTrue(shallowMatch.getType() == shallowCopyMatch.getType());
        assertTrue(shallowMatch.getPlayersDetail() == shallowCopyMatch.getPlayersDetail());
    }

}