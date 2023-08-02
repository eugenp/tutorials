package com.baeldung.conditionals;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class YieldTest {

    enum Number {
        ONE, TWO, THREE, FOUR
    }

    @Test
    void whenSwitchingOnNumberOne_thenWillReturnString() {
        Number number = Number.ONE;
        String message;
        switch (number) {
            case ONE:
                message = "Got a 1";
                break;
            case TWO:
                message = "Got a 2";
                break;
            default:
                message = "More than 2";
        }

        assertEquals("Got a 1", message);
    }

    @Test
    void whenSwitchingWithArrowOnNumberTwo_thenWillReturnString() {
        Number number = Number.TWO;
        String message = switch (number) {
            case ONE -> {
                yield "Got a 1";
            }
            case TWO -> {
                yield "Got a 2";
            }
            default -> {
                yield "More than 2";
            }
        };

        assertEquals("Got a 2", message);
    }

    @Test
    void whenSwitchingWithArrowNoDefaultOnNumberTwo_thenWillReturnString() {
        Number number = Number.TWO;
        String message = switch (number) {
            case ONE -> {
                yield "Got a 1";
            }
            case TWO -> {
                yield "Got a 2";
            }
            case THREE, FOUR -> {
                yield "More than 2";
            }
        };

        assertEquals("Got a 2", message);
    }

    @Test
    void whenSwitchingWithColonOnNumberTwo_thenWillReturnString() {
        Number number = Number.TWO;
        String message = switch (number) {
            case ONE:
                yield "Got a 1";
            case TWO:
                yield "Got a 2";
            default:
                yield "More than 2";
        };

        assertEquals("Got a 2", message);
    }

}
