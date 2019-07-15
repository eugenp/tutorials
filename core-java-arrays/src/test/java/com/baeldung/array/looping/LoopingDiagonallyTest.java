package com.baeldung.array.looping;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoopDiagonallyTest {

    @Test
    public void twoArrayIsLoopedDiagonallyAsExpected() {

        LoopDiagonally loopDiagonally = new LoopDiagonally();
        StringBuilder builder = new StringBuilder();
        String[][] twoDArray = {{"a", "b", "c"},
                {"d", "e", "f"},
                {"g", "h", "i"}};

        loopDiagonally.loopDiagonally(twoDArray, builder);
        assertEquals("a db gec hf i", builder.toString());
    }
}