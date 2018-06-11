package com.baeldung.arrays;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ArraysUnitTest {
    private String[] intro;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        intro = new String[] { "once", "upon", "a", "time" };
    }

    @Test
    public void whenCopyOfRange_thenAbridgedArray() {
        String[] abridgement = Arrays.copyOfRange(intro, 0, 3);

        assertArrayEquals(new String[] { "once", "upon", "a" }, abridgement);
        assertFalse(Arrays.equals(intro, abridgement));
    }

    @Test
    public void whenCopyOf_thenNullElement() {
        String[] revised = Arrays.copyOf(intro, 3);
        String[] expanded = Arrays.copyOf(intro, 5);

        assertArrayEquals(Arrays.copyOfRange(intro, 0, 3), revised);
        assertNull(expanded[4]);
    }

    @Test
    public void whenFill_thenAllMatch() {
        String[] stutter = new String[3];
        Arrays.fill(stutter, "once");

        assertTrue(Stream.of(stutter).allMatch(el -> "once".equals(el)));
    }

    @Test
    public void whenEqualsContent_thenMatch() {
        assertTrue(Arrays.equals(new String[] { "once", "upon", "a", "time" }, intro));
        assertFalse(Arrays.equals(new String[] { "once", "upon", "a", null }, intro));
    }

    @Test
    public void whenNestedArrays_thenDeepEqualsPass() {
        String[] end = { "the", "end" };
        Object[] story = new Object[] { intro, new String[] { "chapter one", "chapter two" }, end };
        Object[] copy = new Object[] { intro, new String[] { "chapter one", "chapter two" }, end };

        assertTrue(Arrays.deepEquals(story, copy));
        assertFalse(Arrays.equals(story, copy));
    }

    @Test
    public void whenSort_thenArraySorted() {
        String[] sorted = Arrays.copyOf(intro, 4);
        Arrays.sort(sorted);

        assertArrayEquals(new String[] { "a", "once", "time", "upon" }, sorted);
    }

    @Test
    public void whenBinarySearch_thenFindElements() {
        String[] sorted = Arrays.copyOf(intro, 4);
        Arrays.sort(sorted);
        int exact = Arrays.binarySearch(sorted, "time");
        int caseInsensitive = Arrays.binarySearch(sorted, "TiMe", String::compareToIgnoreCase);

        assertEquals("time", sorted[exact]);
        assertEquals(2, exact);
        assertEquals(exact, caseInsensitive);
    }

    @Test
    public void whenNullElement_thenArraysHashCodeNotEqual() {
        int beforeChange = Arrays.hashCode(intro);
        int before = intro.hashCode();
        intro[3] = null;
        int after = intro.hashCode();
        int afterChange = Arrays.hashCode(intro);

        assertNotEquals(beforeChange, afterChange);
        assertEquals(before, after);
    }

    @Test
    public void whenNestedArrayNullElement_thenEqualsFailDeepHashPass() {
        Object[] looping = new Object[] { intro, intro };
        int deepHashBefore = Arrays.deepHashCode(looping);
        int hashBefore = Arrays.hashCode(looping);

        intro[3] = null;

        int hashAfter = Arrays.hashCode(looping);
        int deepHashAfter = Arrays.deepHashCode(looping);

        assertEquals(hashAfter, hashBefore);
        assertNotEquals(deepHashAfter, deepHashBefore);
    }

    @Test
    public void whenStreamBadIndex_thenException() {
        assertEquals(Arrays.stream(intro).count(), 4);

        exception.expect(ArrayIndexOutOfBoundsException.class);
        Arrays.stream(intro, 2, 1).count();
    }

    @Test
    public void whenSetAllToUpper_thenAppliedToAllElements() {
        String[] longAgo = new String[4];
        Arrays.setAll(longAgo, i -> intro[i].toUpperCase());

        assertArrayEquals(longAgo, new String[] { "ONCE", "UPON", "A", "TIME" });
    }

    @Test
    public void whenToString_thenFormattedArrayString() {
        assertEquals("[once, upon, a, time]", Arrays.toString(intro));
    }

    @Test
    public void whenNestedArrayDeepString_thenFormattedArraysString() {
        String[] end = { "the", "end" };
        Object[] story = new Object[] { intro, new String[] { "chapter one", "chapter two" }, end };

        assertEquals("[[once, upon, a, time], [chapter one, chapter two], [the, end]]", Arrays.deepToString(story));
    }

    @Test
    public void whenAsList_thenImmutableArray() {
        List<String> rets = Arrays.asList(intro);

        assertTrue(rets.contains("upon"));
        assertTrue(rets.contains("time"));
        assertEquals(rets.size(), 4);

        exception.expect(UnsupportedOperationException.class);
        rets.add("the");
    }
}
