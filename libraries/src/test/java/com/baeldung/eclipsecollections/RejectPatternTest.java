package com.baeldung.eclipsecollections;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.junit.Assert;
import org.junit.Test;

public class RejectPatternTest {

    @Test
    public void whenReject_thenCorrect() {
        MutableList<Integer> list = new FastList<>();
        list.add(1);
        list.add(8);
        list.add(5);
        list.add(41);
        list.add(31);
        list.add(17);
        list.add(23);
        list.add(38);

        MutableList<Integer> notGreaterThanThirty = list.reject(Predicates.greaterThan(30))
            .sortThis();

        Assert.assertEquals(1, (int) notGreaterThanThirty.getFirst());
        Assert.assertEquals(5, (int) notGreaterThanThirty.get(1));
        Assert.assertEquals(8, (int) notGreaterThanThirty.get(2));
        Assert.assertEquals(17, (int) notGreaterThanThirty.get(3));
        Assert.assertEquals(23, (int) notGreaterThanThirty.getLast());
    }
}
