package com.baeldung.arrays;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Stream;


/**
 * @see java.util.Arrays#equals(Object[] a, Object[] a2)
 */
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

        System.out.println("abridgement:{}" + abridgement);

        assertArrayEquals(new String[] { "once", "upon", "a" }, abridgement);
        assertFalse(Arrays.equals(intro, abridgement));
    }

    @Test
    public void whenCopyOf_thenNullElement() {
        //改进的
        String[] revised = Arrays.copyOf(intro, 3);
        //扩充的；展开的
        String[] expanded = Arrays.copyOf(intro, 5);

        System.out.println("revised:{}" + revised);
        System.out.println("expanded:{}" + expanded);

        Assert.assertArrayEquals(Arrays.copyOfRange(intro, 0, 3), revised);
        Assert.assertNull(expanded[4]);
    }

    @Test
    public void whenFill_thenAllMatch() {
        String[] stutter = new String[3];
        Arrays.fill(stutter, "once");

        System.out.println("stutter:{}" + Arrays.toString(stutter));

        boolean b = true;
        for (String el : stutter) {
            if (!"once".equals(el)) {
                b = false;
                break;
            }
        }
        assertTrue(b);
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
        System.out.println("sorted:{}" + Arrays.toString(sorted));

        assertArrayEquals(new String[] { "a", "once", "time", "upon" }, sorted);
    }

    @Test
    public void whenBinarySearch_thenFindElements() {
        String[] sorted = Arrays.copyOf(intro, 4);
        Arrays.sort(sorted);
        System.out.println("sorted:{}" + Arrays.toString(sorted));

        int exact = Arrays.binarySearch(sorted, "time");
        int caseInsensitive = Arrays.binarySearch(sorted, "TiMe", new Comparator<String>() {
            @Override
            public int compare(String s, String str) {
                return s.compareToIgnoreCase(str);
            }
        });

        System.out.println("exact:{}" + exact);
        System.out.println("caseInsensitive:{}" + caseInsensitive);

        assertEquals("time", sorted[exact]);
        assertEquals(2, exact);
        assertEquals(exact, caseInsensitive);
    }

    /**
     * @see  java.util.Arrays#hashCode(Object[] a)
     * 注意1：使用{@link java.util.Arrays#hashCode(Object[] a)}获取数组的hashCode过程中，如果数组的值变化了，那么获取的hashCode会改变。
     * 注意2：改变数组 String[]的值，并不能影响其hashCode的值。
     */
    @Test
    public void whenNullElement_thenArraysHashCodeNotEqual() {
        System.out.println("intro:{}" + Arrays.toString(intro));

        int beforeChange = Arrays.hashCode(intro);
        int before = intro.hashCode();

        System.out.println("beforeChange:{} , before:{} "  + beforeChange + " , " + before);

        intro[3] = null;
        System.out.println("intro 3 set null:{}" + Arrays.toString(intro));

        int after = intro.hashCode();
        int afterChange = Arrays.hashCode(intro);

        System.out.println("after:{} , afterChange:{} "  + after + " , " + afterChange);

        assertNotEquals(beforeChange, afterChange);
        assertEquals(before, after);
    }

    /**
     * @see  java.util.Arrays#deepHashCode(Object[] a)
     */
    @Test
    public void whenNestedArrayNullElement_thenEqualsFailDeepHashPass() {
        Object[] looping = new Object[] { intro, intro };
        //初始化时，打印数组
        for(Object element : looping){
            System.out.println(Arrays.toString((String[])element));
        }

        int deepHashBefore = Arrays.deepHashCode(looping);
        int hashBefore = Arrays.hashCode(looping);

        System.out.println("deepHashBefore:{} , hashBefore:{} "  + deepHashBefore + " , " + hashBefore);

        intro[3] = null;
        //改变值后，打印数组
        for(Object element : looping){
            System.out.println(Arrays.toString((String[])element));
        }

        int hashAfter = Arrays.hashCode(looping);
        int deepHashAfter = Arrays.deepHashCode(looping);

        System.out.println("hashAfter:{} , deepHashAfter:{} "  + hashAfter + " , " + deepHashAfter);

        assertEquals(hashAfter, hashBefore);
        assertNotEquals(deepHashAfter, deepHashBefore);
    }

    /**
     * @see java.util.Arrays#stream(Object[] array, int startInclusive, int endExclusive)
     */
    @Test
    public void whenStreamBadIndex_thenException() {
        System.out.println("intro:{}" + Arrays.toString(intro));

        assertEquals(Arrays.stream(intro).count(), 4);

        exception.expect(ArrayIndexOutOfBoundsException.class);


        //long count = Arrays.<String>stream(intro, 0, 3).count();
        //System.out.println("count:{}" + count);

        Object[] strings = Arrays.<String>stream(intro, 0, 3).toArray();
        System.out.println("intro after use stream:{}" + Arrays.toString(strings));
    }

    @Test
    public void whenSetAllToUpper_thenAppliedToAllElements() {
        String[] longAgo = new String[4];
        Arrays.setAll(longAgo, new IntFunction<String>() {
            @Override
            public String apply(int i) {
                return intro[i].toUpperCase();
            }
        });

        System.out.println("longAgo:{}" + Arrays.toString(longAgo));

        Assert.assertArrayEquals(longAgo, new String[] { "ONCE", "UPON", "A", "TIME" });
    }

    @Test
    public void whenToString_thenFormattedArrayString() {
        System.out.println("intro:{}" + Arrays.toString(intro));
        Assert.assertEquals("[once, upon, a, time]", Arrays.toString(intro));
    }

    @Test
    public void whenNestedArrayDeepString_thenFormattedArraysString() {
        String[] end = { "the", "end" };
        Object[] story = new Object[] { intro, new String[] { "chapter one", "chapter two" }, end };
        for(Object obj : story){
            System.out.println("obj info:{}" + Arrays.toString((String[])obj));
        }

        Assert.assertEquals("[[once, upon, a, time], [chapter one, chapter two], [the, end]]", Arrays.deepToString(story));
    }

    /**
     * @see java.util.Arrays#asList(Object[]) 得到的是一个不可变的数组，执行add会报错。
     */
    @Test
    public void whenAsList_thenImmutableArray() {
        List<String> rets = Arrays.asList(intro);
        System.out.println("intro:{}" + Arrays.toString(intro));
        System.out.println("rets:{}" + rets);

        Assert.assertTrue(rets.contains("upon"));
        Assert.assertTrue(rets.contains("time"));
        Assert.assertEquals(rets.size(), 4);

        exception.expect(UnsupportedOperationException.class);
        rets.add("the");
    }
}
