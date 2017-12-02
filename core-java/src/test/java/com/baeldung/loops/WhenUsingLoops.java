package com.baeldung.loops;

import org.junit.Assert;
import org.junit.Test;

public class WhenUsingLoops {

    private LoopsInJava loops = new LoopsInJava();

    @Test
    public void shouldRunForLoop() {
        int[] expected = { 0, 1, 2, 3, 4 };
        int[] actual = loops.simple_for_loop();
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldRunEnhancedForeachLoop() {
        int[] expected = { 0, 1, 2, 3, 4 };
        int[] actual = loops.enhanced_for_each_loop();
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldRunWhileLoop() {
        int[] expected = { 0, 1, 2, 3, 4 };
        int[] actual = loops.while_loop();
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldRunDoWhileLoop() {
        int[] expected = { 0, 1, 2, 3, 4 };
        int[] actual = loops.do_while_loop();
        Assert.assertArrayEquals(expected, actual);
    }
}
