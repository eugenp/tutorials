package com.baeldung.arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
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

    @Test
    public void givenIntArray_whenPrefixAdd_thenAllAccumulated() {
        int[] arri = new int[] { 1, 2, 3, 4};
        Arrays.parallelPrefix(arri, (left, right) -> left + right);
        assertThat(arri, is(new int[] { 1, 3, 6, 10}));
    }

    @Test
    public void givenStringArray_whenPrefixConcat_thenAllMerged() {
        String[] arrs = new String[] { "1", "2", "3" };
        Arrays.parallelPrefix(arrs, (left, right) -> left + right);
        assertThat(arrs, is(new String[] { "1", "12", "123" }));
    }

    @Test
    public void whenPrefixAddWithRange_thenRangeAdded() {
        int[] arri = new int[] { 1, 2, 3, 4, 5 };
        Arrays.parallelPrefix(arri, 1, 4, (left, right) -> left + right);
        assertThat(arri, is(new int[] { 1, 2, 5, 9, 5 }));
    }

    @Test
    public void whenPrefixNonAssociative_thenError() {
        boolean consistent = true;
        Random r = new Random();
        for (int k = 0; k < 100_000; k++) {
            int[] arrA = r.ints(100, 1, 5).toArray();
            int[] arrB = Arrays.copyOf(arrA, arrA.length);

            Arrays.parallelPrefix(arrA, this::nonassociativeFunc);

            for (int i = 1; i < arrB.length; i++) {
                arrB[i] = nonassociativeFunc(arrB[i - 1], arrB[i]);
            }
            consistent = Arrays.equals(arrA, arrB);
            if(!consistent) break;
        }
        assertFalse(consistent);
    }

    /**
     * non-associative int binary operator
     */
    private int nonassociativeFunc(int left, int right) {
        return left + right*left;
    }

}
