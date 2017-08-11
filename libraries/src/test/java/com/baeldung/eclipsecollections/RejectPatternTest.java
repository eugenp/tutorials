package com.baeldung.eclipsecollections;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.list.mutable.FastList;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class RejectPatternTest {

    MutableList<Integer> list;

    @Before
    public void getList() {
        this.list = new FastList<>();
        list.add(1);
        list.add(8);
        list.add(5);
        list.add(41);
        list.add(31);
        list.add(17);
        list.add(23);
        list.add(38);
    }

    @Test
    public void whenReject_thenCorrect() {
        MutableList<Integer> notGreaterThanThirty = list.reject(Predicates.greaterThan(30))
            .sortThis();

        assertEquals(1, (int) notGreaterThanThirty.getFirst());
        assertEquals(5, (int) notGreaterThanThirty.get(1));
        assertEquals(8, (int) notGreaterThanThirty.get(2));
        assertEquals(17, (int) notGreaterThanThirty.get(3));
        assertEquals(23, (int) notGreaterThanThirty.getLast());
    }
}
