package com.baeldung.newfeatures;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SwitchExpressionsWithYieldUnitTest {

    @Test
    public void whenSwitchingOnOperationSquareMe_thenWillReturnSquare() {
        var me = 4;
        var operation = "squareMe";
        var result = switch (operation) {
        case "doubleMe" -> {
            yield me * 2;
        }
        case "squareMe" -> {
            yield me * me;
        }
        default -> me;
        };

        assertEquals(16, result);
    }

}
