package com.baeldung.compareto;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class HandballPlayerUnitTest {

    @Test
    public void givenComparableIsNotImplemented_whenSortingArray_thenExceptionIsThrown() {
        HandballPlayer duvnjak = new HandballPlayer("Duvnjak", 197);
        HandballPlayer hansen = new HandballPlayer("Hansen", 196);

        HandballPlayer[] players = new HandballPlayer[] {duvnjak, hansen};

        assertThatExceptionOfType(ClassCastException.class).isThrownBy(() -> Arrays.sort(players));
    }

}
