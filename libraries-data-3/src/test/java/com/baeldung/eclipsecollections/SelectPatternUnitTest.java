package com.baeldung.eclipsecollections;

import org.assertj.core.api.Assertions;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.junit.Before;
import org.junit.Test;

public class SelectPatternUnitTest {

    MutableList<Integer> list;

    @Before
    public void getList() {
        this.list = FastList.newListWith(1, 8, 5, 41, 31, 17, 23, 38);
    }

    @Test
    public void givenListwhenSelect_thenCorrect() {
        MutableList<Integer> greaterThanThirty = list.select(Predicates.greaterThan(30)).sortThis();

        Assertions.assertThat(greaterThanThirty).containsExactly(31, 38, 41);
    }

    @SuppressWarnings("rawtypes")
    public MutableList selectUsingLambda() {
        return list.select(each -> each > 30).sortThis();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void givenListwhenSelectUsingLambda_thenCorrect() {
        MutableList<Integer> greaterThanThirty = selectUsingLambda();

        Assertions.assertThat(greaterThanThirty).containsExactly(31, 38, 41);
    }
}
