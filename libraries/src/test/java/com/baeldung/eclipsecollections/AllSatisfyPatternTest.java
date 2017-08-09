package com.baeldung.eclipsecollections;

import static org.junit.Assert.assertTrue;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.junit.Test;

public class AllSatisfyPatternTest {

    @Test
    public void whenAnySatisfiesCondition_thenCorrect() {
        MutableList<Integer> list = new FastList<>();
        list.add(1);
        list.add(8);
        list.add(5);
        list.add(41);
        list.add(31);
        list.add(17);
        list.add(23);
        list.add(38);

        boolean result = list.allSatisfy(Predicates.greaterThan(0));

        assertTrue(result);
    }
}
