package com.baeldung.folding;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FoldingHashUnitTest {

    @Test
    public void givenStringJavaLanguage_whenSize2Capacity100000_then48933() throws Exception {
        final FoldingHash hasher = new FoldingHash();
        final int value = hasher.hash("Java language", 2, 100_000);
        assertEquals(value, 48933);
    }

    @Test
    public void givenStringVaJaLanguage_whenSize2Capacity100000_thenSameAsJavaLanguage() throws Exception {
        final FoldingHash hasher = new FoldingHash();
        final int java = hasher.hash("Java language", 2, 100_000);
        final int vaja = hasher.hash("vaJa language", 2, 100_000);
        assertTrue(java == vaja);
    }

    @Test
    public void givenSingleElementArray_whenOffset0Size2_thenSingleElement() throws Exception {
        final FoldingHash hasher = new FoldingHash();
        final int[] value = hasher.extract(new int[] { 5 }, 0, 2);
        assertArrayEquals(new int[] { 5 }, value);
    }

    @Test
    public void givenFiveElementArray_whenOffset0Size3_thenFirstThreeElements() throws Exception {
        final FoldingHash hasher = new FoldingHash();
        final int[] value = hasher.extract(new int[] { 1, 2, 3, 4, 5 }, 0, 3);
        assertArrayEquals(new int[] { 1, 2, 3 }, value);
    }

    @Test
    public void givenFiveElementArray_whenOffset1Size2_thenTwoElements() throws Exception {
        final FoldingHash hasher = new FoldingHash();
        final int[] value = hasher.extract(new int[] { 1, 2, 3, 4, 5 }, 1, 2);
        assertArrayEquals(new int[] { 2, 3 }, value);
    }

    @Test
    public void givenFiveElementArray_whenOffset2SizeTooBig_thenElementsToTheEnd() throws Exception {
        final FoldingHash hasher = new FoldingHash();
        final int[] value = hasher.extract(new int[] { 1, 2, 3, 4, 5 }, 2, 2000);
        assertArrayEquals(new int[] { 3, 4, 5 }, value);
    }

}
