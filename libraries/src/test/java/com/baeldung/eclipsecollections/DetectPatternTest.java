package com.baeldung.eclipsecollections;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.list.mutable.FastList;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class DetectPatternTest {

    @Test
    public void whenDetect_thenCorrect() {
        MutableList<Integer> list = new FastList<>();
        list.add(1);
        list.add(8);
        list.add(5);
        list.add(41);
        list.add(31);
        list.add(17);
        list.add(23);
        list.add(38);

        Integer result = list.detect(Predicates.greaterThan(30));

        assertEquals((int) result, 41);
    }
}
