package com.baeldung.eclipsecollections;

import org.assertj.core.api.Assertions;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.junit.Before;
import org.junit.Test;

public class DetectPatternUnitTest {

    MutableList<Integer> list;

    @Before
    public void getList() {
        this.list = FastList.newListWith(1, 8, 5, 41, 31, 17, 23, 38);
    }

    @Test
    public void whenDetect_thenCorrect() {
        Integer result = list.detect(Predicates.greaterThan(30));

        Assertions.assertThat(result).isEqualTo(41);
    }
}
