package com.baeldung.arrays;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ArraysTest {
    private String[] intro = new String[] { "once", "upon", "a", "time" };

    @Test
    public void testArraysCopyOfRange() {
        String[] abridgement = Arrays.copyOfRange(intro, 0, 3);

        Assert.assertArrayEquals(new String[] { "once", "upon", "a" }, abridgement);
        Assert.assertFalse(Arrays.equals(intro, abridgement));
    }

    @Test
    public void testArraysCopyOf(){
        String[] revised = Arrays.copyOf(intro, 3);
        String[] expanded = Arrays.copyOf(intro, 5);

        Assert.assertArrayEquals(Arrays.copyOfRange(intro, 0, 3), revised);
        Assert.assertNull(expanded[4]);
    }

    @Test
    public void testArraysFill(){
        String[] stutter = new String[3];
        Arrays.fill(stutter, "once");

        Assert.assertTrue(Stream.of(stutter)
                .allMatch(el -> "once".equals(el)));
    }

    @Test
    public void testArraysEquals(){
        Assert.assertTrue(Arrays.equals(new String[]{"once","upon","a","time"}, intro));
        Assert.assertFalse(Arrays.equals(new String[]{"once","upon","a", null}, intro));
    }

    @Test
    public void testArraysDeepEquals() {
        String[] end = {"the", "end"};
        Object[] story =
                new Object[] { intro, new String[] { "chapter one", "chapter two" }, end };
        Object[] copy =
                new Object[] { intro, new String[] { "chapter one", "chapter two" }, end };

        Assert.assertTrue(Arrays.deepEquals(story, copy));
        Assert.assertFalse(Arrays.equals(story, copy));
    }

    @Test
    public void testArraysSort(){
        String[] sorted = Arrays.copyOf(intro, 4);
        Arrays.sort(sorted);

        Assert.assertArrayEquals(new String[]{"a", "once", "time", "upon"}, sorted);
    }

    @Test
    public void testArraysBinarySearch(){
        String[] sorted = Arrays.copyOf(intro, 4);
        Arrays.sort(sorted);
        int exact = Arrays.binarySearch(sorted, "time");
        int caseInsensitive = Arrays.binarySearch(sorted, "TiMe", String::compareToIgnoreCase);

        Assert.assertEquals("time", sorted[exact]);
        Assert.assertEquals(2, exact);
        Assert.assertEquals(exact, caseInsensitive);
    }

    @Test
    public void testArraysHashCode(){
        int beforeChange = Arrays.hashCode(intro);
        intro[3] = null;
        int afterChange = Arrays.hashCode(intro);

        Assert.assertNotEquals(beforeChange, afterChange);
    }

    @Test
    public void testArraysDeepHash(){
        Object[] looping = new Object[]{ intro, intro };
        int deepHashBefore = Arrays.deepHashCode(looping);
        int hashBefore = Arrays.hashCode(looping);

        intro[3] = null;

        int hashAfter = Arrays.hashCode(looping);
        int deepHashAfter = Arrays.deepHashCode(looping);

        Assert.assertEquals(hashAfter, hashBefore);
        Assert.assertNotEquals(deepHashAfter, deepHashBefore);
    }

    @Test
    public void testArraysToString(){
        Assert.assertEquals("[once, upon, a, time]", Arrays.toString(intro));
    }

    @Test
    public void testArraysAsList() {
        List<String> rets = Arrays.asList(intro);

        Assert.assertTrue(rets.contains("upon"));
        Assert.assertTrue(rets.contains("time"));
        Assert.assertEquals(rets.size(), 4);
    }
}
