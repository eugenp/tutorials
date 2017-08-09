package com.baeldung.eclipsecollections;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.junit.Assert;
import org.junit.Test;

public class SelectPatternTest {

    @Test
    public void whenSelect_thenCorrect() {
        MutableList<Integer> list = new FastList<>();
        list.add(1);
        list.add(8);
        list.add(5);
        list.add(41);
        list.add(31);
        list.add(17);
        list.add(23);
        list.add(38);

        MutableList<Integer> greaterThanThirty = list.select(Predicates.greaterThan(30))
            .sortThis();

        Assert.assertEquals(31, (int) greaterThanThirty.getFirst());
        Assert.assertEquals(38, (int) greaterThanThirty.get(1));
        Assert.assertEquals(41, (int) greaterThanThirty.getLast());
    }

    public MutableList selectUsingLambda() {
        MutableList<Integer> list = new FastList<>();
        list.add(1);
        list.add(8);
        list.add(5);
        list.add(41);
        list.add(31);
        list.add(17);
        list.add(23);
        list.add(38);

        return list.select(each -> each > 30)
            .sortThis();
    }

    @Test
    public void whenSelectUsingLambda_thenCorrect() {
        MutableList<Integer> greaterThanThirty = selectUsingLambda();

        Assert.assertEquals(31, (int) greaterThanThirty.getFirst());
        Assert.assertEquals(38, (int) greaterThanThirty.get(1));
        Assert.assertEquals(41, (int) greaterThanThirty.getLast());
    }
}
