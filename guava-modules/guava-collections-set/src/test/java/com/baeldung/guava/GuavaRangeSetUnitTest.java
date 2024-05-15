package com.baeldung.guava;

import com.google.common.collect.ImmutableRangeSet;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;
import org.junit.Test;

import static org.junit.Assert.*;

public class GuavaRangeSetUnitTest {

    @Test
    public void givenRangeSet_whenQueryWithinRange_returnsSucessfully() {
        final RangeSet<Integer> numberRangeSet = TreeRangeSet.create();

        numberRangeSet.add(Range.closed(0, 2));
        numberRangeSet.add(Range.closed(3, 5));
        numberRangeSet.add(Range.closed(6, 8));

        assertTrue(numberRangeSet.contains(1));
        assertFalse(numberRangeSet.contains(9));
    }

    @Test
    public void givenRangeSet_whenEnclosesWithinRange_returnsSucessfully() {
        final RangeSet<Integer> numberRangeSet = TreeRangeSet.create();

        numberRangeSet.add(Range.closed(0, 2));
        numberRangeSet.add(Range.closed(3, 10));
        numberRangeSet.add(Range.closed(15, 18));

        assertTrue(numberRangeSet.encloses(Range.closed(4, 5)));
        assertFalse(numberRangeSet.encloses(Range.closed(4, 11)));
    }

    @Test
    public void givenRangeSet_whenComplementIsCalled_returnsSucessfully() {
        final RangeSet<Integer> numberRangeSet = TreeRangeSet.create();

        numberRangeSet.add(Range.closed(0, 2));
        numberRangeSet.add(Range.closed(3, 5));
        numberRangeSet.add(Range.closed(6, 8));
        final RangeSet<Integer> numberRangeComplementSet = numberRangeSet.complement();

        assertTrue(numberRangeComplementSet.contains(-1000));
        assertFalse(numberRangeComplementSet.contains(2));
        assertFalse(numberRangeComplementSet.contains(3));
        assertTrue(numberRangeComplementSet.contains(1000));
    }

    @Test
    public void givenRangeSet_whenIntersectsWithinRange_returnsSucessfully() {
        final RangeSet<Integer> numberRangeSet = TreeRangeSet.create();

        numberRangeSet.add(Range.closed(0, 2));
        numberRangeSet.add(Range.closed(3, 10));
        numberRangeSet.add(Range.closed(15, 18));

        assertTrue(numberRangeSet.intersects(Range.closed(4, 17)));
        assertFalse(numberRangeSet.intersects(Range.closed(19, 200)));
    }

    @Test
    public void givenRangeSet_whenRemoveRangeIsCalled_removesSucessfully() {
        final RangeSet<Integer> numberRangeSet = TreeRangeSet.create();

        numberRangeSet.add(Range.closed(0, 2));
        numberRangeSet.add(Range.closed(3, 5));
        numberRangeSet.add(Range.closed(6, 8));
        numberRangeSet.add(Range.closed(9, 15));
        numberRangeSet.remove(Range.closed(3, 5));
        numberRangeSet.remove(Range.closed(7, 10));

        assertTrue(numberRangeSet.contains(1));
        assertFalse(numberRangeSet.contains(9));
        assertTrue(numberRangeSet.contains(12));
    }

    @Test
    public void givenRangeSet_whenSpanIsCalled_returnsSucessfully() {
        final RangeSet<Integer> numberRangeSet = TreeRangeSet.create();

        numberRangeSet.add(Range.closed(0, 2));
        numberRangeSet.add(Range.closed(3, 5));
        numberRangeSet.add(Range.closed(6, 8));
        final Range<Integer> experienceSpan = numberRangeSet.span();

        assertEquals(0, experienceSpan.lowerEndpoint().intValue());
        assertEquals(8, experienceSpan.upperEndpoint().intValue());
    }

    @Test
    public void givenRangeSet_whenSubRangeSetIsCalled_returnsSubRangeSucessfully() {
        final RangeSet<Integer> numberRangeSet = TreeRangeSet.create();

        numberRangeSet.add(Range.closed(0, 2));
        numberRangeSet.add(Range.closed(3, 5));
        numberRangeSet.add(Range.closed(6, 8));
        final RangeSet<Integer> numberSubRangeSet = numberRangeSet.subRangeSet(Range.closed(4, 14));

        assertFalse(numberSubRangeSet.contains(3));
        assertFalse(numberSubRangeSet.contains(14));
        assertTrue(numberSubRangeSet.contains(7));
    }

    @Test
    public void givenImmutableRangeSet_whenQueryWithinRange_returnsSucessfully() {
        final RangeSet<Integer> numberRangeSet = ImmutableRangeSet.<Integer> builder()
	    .add(Range.closed(0, 2))
	    .add(Range.closed(3, 5))
	    .add(Range.closed(6, 8)).build();

        assertTrue(numberRangeSet.contains(6));
        assertFalse(numberRangeSet.contains(15));
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenImmutableRangeMap_whenRangeOverlaps_ThrowsException() {
        ImmutableRangeSet.<Integer> builder()
	    .add(Range.closed(0, 2))
	    .add(Range.closed(3, 5))
	    .add(Range.closed(5, 8)).build();
    }
}
