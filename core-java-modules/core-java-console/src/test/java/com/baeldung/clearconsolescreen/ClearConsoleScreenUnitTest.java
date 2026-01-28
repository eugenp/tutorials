package com.baeldung.clearconsolescreen;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ClearConsoleScreenUnitTest {

    @Test
    void givenAnsiClearMethod_whenInvoked_thenDoesNotThrowException() {
        assertDoesNotThrow(ClearConsoleScreen::clearWithANSICodes);
    }

    @Test
    void givenBlankLineClearMethod_whenInvoked_thenDoesNotThrowException() {
        assertDoesNotThrow(ClearConsoleScreen::clearWithBlankLines);
    }

    @Test
    void givenLinuxClearMethod_whenInvoked_thenDoesNotThrowException() {
        assertDoesNotThrow(ClearConsoleScreen::clearWithLinuxCommand);
    }
}
