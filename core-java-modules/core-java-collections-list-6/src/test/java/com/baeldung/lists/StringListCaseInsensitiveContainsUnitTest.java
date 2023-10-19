package com.baeldung.lists;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class StringListCaseInsensitiveContainsUnitTest {
    private final static List<String> THE_LIST = List.of("Game of Thrones", "Forrest Gump", "American Beauty", "Pretty Woman", "Catch Me If You Can");

    @Test
    void whenUsingContains_thenGetExpectedResult() {
        assertFalse(THE_LIST.contains("catch me if you can"));
    }

    boolean ignoreCaseContainsForLoop(List<String> list, String value) {
        for (String e : list) {
            if (value.equalsIgnoreCase(e))
                return true;
        }
        return false;
    }

    @Test
    void whenUsingIgnoreCaseContainsForLoop_thenGetExpectedResult() {
        assertTrue(ignoreCaseContainsForLoop(THE_LIST, "CATCH me if you CAN"));
        assertTrue(ignoreCaseContainsForLoop(THE_LIST, "game of thrones"));
        assertFalse(ignoreCaseContainsForLoop(THE_LIST, "The Godfather"));
    }

    @Test
    void whenUsingIgnoreCaseContainsStream_thenGetExpectedResult() {
        assertTrue(THE_LIST.stream()
          .anyMatch(e -> e.equalsIgnoreCase("CATCH me if you CAN")));

        assertTrue(THE_LIST.stream()
          .anyMatch("game of thrones"::equalsIgnoreCase));

        assertFalse(THE_LIST.stream()
          .anyMatch("The Godfather"::equalsIgnoreCase));
    }
}