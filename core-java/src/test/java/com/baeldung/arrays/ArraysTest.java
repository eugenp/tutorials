package com.baeldung.arrays;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ArraysTest {
    private String[] storyIntro = new String[] { "once", "upon", "a", "time" };

    @Test
    public void testArraysCopyOfRange() {
        String[] abridgement = Arrays.copyOfRange(storyIntro, 0, 3);

        Assert.assertArrayEquals(new String[]{"once", "upon", "a"}, abridgement);
        Assert.assertFalse(Arrays.equals(storyIntro, abridgement));
    }

    @Test
    public void testArraysCopyOf(){
        String[] editedIntro = Arrays.copyOf(storyIntro, 3);
        String[] draftIntro = Arrays.copyOf(storyIntro, 5);

        Assert.assertArrayEquals(Arrays.copyOfRange(storyIntro, 0, 3), editedIntro);
        Assert.assertNull(draftIntro[4]);
    }

    @Test
    public void testArraysFill(){
        String[] repetitiveIntro = new String[4];
        Arrays.fill(repetitiveIntro, "once");

        Assert.assertEquals(repetitiveIntro[0], repetitiveIntro[3]);
    }

    @Test
    public void testArraysEquals(){
        Assert.assertTrue(Arrays.equals(new String[]{"once","upon","a","time"}, storyIntro));
        Assert.assertFalse(Arrays.equals(new String[]{"once","upon","a", null}, storyIntro));
    }

    @Test
    public void testArraysDeepEquals() {
        Object[] metaStoryIntro = new Object[] { storyIntro, "In the beginning",  new String[]{"In a universe..."} };
        Object[] metaStoryIntro2 = new Object[] { storyIntro, "In the beginning", new String[]{"In a universe..."} };

        Assert.assertFalse(Arrays.equals(metaStoryIntro, metaStoryIntro2));
        Assert.assertTrue(Arrays.deepEquals(metaStoryIntro, metaStoryIntro2));
    }

    @Test
    public void testArraysSort(){
        Arrays.sort(storyIntro);

        Assert.assertArrayEquals(new String[]{"a", "once", "time", "upon"}, storyIntro);
    }

    @Test
    public void testArraysBinarySearch(){
        Arrays.sort(storyIntro);
        int index = Arrays.binarySearch(storyIntro, "time", String::compareToIgnoreCase);

        Assert.assertEquals("time", storyIntro[index]);
        Assert.assertEquals(2 , index);
    }

    @Test
    public void testArraysHashCode(){
        int beforeChange = Arrays.hashCode(storyIntro);
        storyIntro[3] = "foo";
        int afterChange = Arrays.hashCode(storyIntro);

        Assert.assertNotEquals(beforeChange, afterChange);
    }

    @Test
    public void testArraysDeepHash(){
        String[][] loopingIntro = new String[][] { storyIntro, storyIntro };
        int deepHashBefore = Arrays.deepHashCode(loopingIntro);
        int hashBefore = Arrays.hashCode(loopingIntro);

        storyIntro[3] = null;

        int hashAfter = Arrays.hashCode(loopingIntro);
        int deepHashAfter = Arrays.deepHashCode(loopingIntro);

        Assert.assertEquals(hashAfter, hashBefore);
        Assert.assertNotEquals(deepHashAfter, deepHashBefore);
    }

    @Test
    public void testArraysToString(){
        Assert.assertEquals("[once, upon, a, time]", Arrays.toString(storyIntro));
    }

    @Test
    public void testArraysAsList() {
        List<String> rets = Arrays.asList(storyIntro);

        Assert.assertTrue(rets.contains("upon"));
        Assert.assertTrue(rets.contains("time"));
        Assert.assertEquals(rets.size(), 4);
    }
}
