package com.baeldung.eclipsecollections;

import static org.junit.Assert.assertTrue;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.junit.Before;
import org.junit.Test;

public class AllSatisfyPatternUnitTest {

    MutableList<Integer> list;

    @Before
    public void getList() {
        this.list = FastList.newListWith(1, 8, 5, 41, 31, 17, 23, 38);
    }

    @Test
    public void whenAnySatisfiesCondition_thenCorrect() {
        boolean result = list.allSatisfy(Predicates.greaterThan(0));

        assertTrue(result);
    }
}
