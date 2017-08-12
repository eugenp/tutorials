package com.baeldung.eclipsecollections;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.list.mutable.FastList;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class RejectPatternTest {

    MutableList<Integer> list;
    MutableList<Integer> expectedList;

    @Before
    public void setup() {
        this.list = FastList.newListWith(1, 8, 5, 41, 31, 17, 23, 38);
        this.expectedList = FastList.newListWith(1, 5, 8, 17, 23);
    }

    @Test
    public void whenReject_thenCorrect() {
        MutableList<Integer> notGreaterThanThirty = list.reject(Predicates.greaterThan(30))
            .sortThis();

        Assertions.assertThat(notGreaterThanThirty)
            .containsExactlyElementsOf(this.expectedList);
    }
}
