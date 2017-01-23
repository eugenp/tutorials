package org.baeldung.guava;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.util.Map;
import org.junit.Test;
import com.google.common.collect.ImmutableRangeMap;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

public class GuavaRangeMapTest {

    @Test
    public void givenRangeMap_whenQueryWithinRange_returnsSucessfully() {
        final RangeMap<Integer, String> experienceRangeDesignationMap = TreeRangeMap.create();

        experienceRangeDesignationMap.put(Range.closed(0, 2), "Associate");
        experienceRangeDesignationMap.put(Range.closed(3, 5), "Senior Associate");
        experienceRangeDesignationMap.put(Range.closed(6, 8), "Vice President");
        experienceRangeDesignationMap.put(Range.closed(9, 15), "Executive Director");

        assertEquals("Vice President", experienceRangeDesignationMap.get(6));
        assertEquals("Executive Director", experienceRangeDesignationMap.get(15));
    }

    @Test
    public void givenRangeMap_whenQueryOutsideRange_returnsNull() {
        final RangeMap<Integer, String> experienceRangeDesignationMap = TreeRangeMap.create();

        experienceRangeDesignationMap.put(Range.closed(0, 2), "Associate");
        experienceRangeDesignationMap.put(Range.closed(3, 5), "Senior Associate");
        experienceRangeDesignationMap.put(Range.closed(6, 8), "Vice President");
        experienceRangeDesignationMap.put(Range.closed(9, 15), "Executive Director");

        assertNull(experienceRangeDesignationMap.get(31));
    }

    @Test
    public void givenRangeMap_whenRemoveRangeIsCalled_removesSucessfully() {
        final RangeMap<Integer, String> experienceRangeDesignationMap = TreeRangeMap.create();

        experienceRangeDesignationMap.put(Range.closed(0, 2), "Associate");
        experienceRangeDesignationMap.put(Range.closed(3, 5), "Senior Associate");
        experienceRangeDesignationMap.put(Range.closed(6, 8), "Vice President");
        experienceRangeDesignationMap.put(Range.closed(9, 15), "Executive Director");
        experienceRangeDesignationMap.remove(Range.closed(9, 15));
        experienceRangeDesignationMap.remove(Range.closed(1, 4));

        assertNull(experienceRangeDesignationMap.get(9));
        assertEquals("Associate", experienceRangeDesignationMap.get(0));
        assertEquals("Senior Associate", experienceRangeDesignationMap.get(5));
        assertNull(experienceRangeDesignationMap.get(1));
    }

    @Test
    public void givenRangeMap_whenSpanIsCalled_returnsSucessfully() {
        final RangeMap<Integer, String> experienceRangeDesignationMap = TreeRangeMap.create();

        experienceRangeDesignationMap.put(Range.closed(0, 2), "Associate");
        experienceRangeDesignationMap.put(Range.closed(3, 5), "Senior Associate");
        experienceRangeDesignationMap.put(Range.closed(6, 8), "Vice President");
        experienceRangeDesignationMap.put(Range.closed(9, 15), "Executive Director");
        final Range<Integer> experienceSpan = experienceRangeDesignationMap.span();

        assertEquals(0, experienceSpan.lowerEndpoint().intValue());
        assertEquals(15, experienceSpan.upperEndpoint().intValue());
    }

    @Test
    public void givenRangeMap_whenGetEntryIsCalled_returnsEntrySucessfully() {
        final RangeMap<Integer, String> experienceRangeDesignationMap = TreeRangeMap.create();

        experienceRangeDesignationMap.put(Range.closed(0, 2), "Associate");
        experienceRangeDesignationMap.put(Range.closed(3, 5), "Senior Associate");
        experienceRangeDesignationMap.put(Range.closed(6, 8), "Vice President");
        experienceRangeDesignationMap.put(Range.closed(9, 15), "Executive Director");
        final Map.Entry<Range<Integer>, String> experiencEntry = experienceRangeDesignationMap.getEntry(10);

        assertEquals(Range.closed(9, 15), experiencEntry.getKey());
        assertEquals("Executive Director", experiencEntry.getValue());
    }

    @Test
    public void givenRangeMap_whenSubRangeMapIsCalled_returnsSubRangeSucessfully() {
        final RangeMap<Integer, String> experienceRangeDesignationMap = TreeRangeMap.create();

        experienceRangeDesignationMap.put(Range.closed(0, 2), "Associate");
        experienceRangeDesignationMap.put(Range.closed(3, 5), "Senior Associate");
        experienceRangeDesignationMap.put(Range.closed(6, 8), "Vice President");
        experienceRangeDesignationMap.put(Range.closed(8, 15), "Executive Director");
        final RangeMap<Integer, String> experiencedSubRangeDesignationMap = experienceRangeDesignationMap.subRangeMap(Range.closed(4, 14));

        assertNull(experiencedSubRangeDesignationMap.get(3));
        assertEquals("Executive Director", experiencedSubRangeDesignationMap.get(14));
        assertEquals("Vice President", experiencedSubRangeDesignationMap.get(7));
    }

    @Test
    public void givenImmutableRangeMap_whenQueryWithinRange_returnsSucessfully() {
        final RangeMap<Integer, String> experienceRangeDesignationMap = ImmutableRangeMap.<Integer, String> builder().put(Range.closed(0, 2), "Associate").put(Range.closed(3, 5), "Senior Associate").put(Range.closed(6, 8), "Vice President")
                .put(Range.closed(9, 15), "Executive Director").build();

        assertEquals("Vice President", experienceRangeDesignationMap.get(6));
        assertEquals("Executive Director", experienceRangeDesignationMap.get(15));
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenImmutableRangeMap_whenRangeOverlaps_ThrowsException() {
        ImmutableRangeMap.<Integer, String> builder().put(Range.closed(0, 2), "Associate").put(Range.closed(3, 5), "Senior Associate").put(Range.closed(6, 8), "Vice President").put(Range.closed(8, 15), "Executive Director").build();

    }
}
