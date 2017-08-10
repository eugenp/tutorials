package com.baeldung.eclipsecollections;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.list.mutable.FastList;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class SelectPatternTest {

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
    public void givenListwhenSelect_thenCorrect() {
        MutableList<Integer> greaterThanThirty = list.select(Predicates.greaterThan(30))
            .sortThis();

        assertEquals(31, (int) greaterThanThirty.getFirst());
        assertEquals(38, (int) greaterThanThirty.get(1));
        assertEquals(41, (int) greaterThanThirty.getLast());
    }

    @SuppressWarnings("rawtypes")
    public MutableList selectUsingLambda() {
        return list.select(each -> each > 30)
            .sortThis();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void givenListwhenSelectUsingLambda_thenCorrect() {
        MutableList<Integer> greaterThanThirty = selectUsingLambda();

        assertEquals(31, (int) greaterThanThirty.getFirst());
        assertEquals(38, (int) greaterThanThirty.get(1));
        assertEquals(41, (int) greaterThanThirty.getLast());
    }
}
