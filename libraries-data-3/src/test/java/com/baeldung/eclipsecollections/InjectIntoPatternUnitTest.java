package com.baeldung.eclipsecollections;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.eclipse.collections.impl.factory.Lists;
import org.junit.Test;

public class InjectIntoPatternUnitTest {

    @Test
    public void whenInjectInto_thenCorrect() {
        List<Integer> list = Lists.mutable.of(1, 2, 3, 4);
        int result = 5;
        for (int i = 0; i < list.size(); i++) {
            Integer v = list.get(i);
            result = result + v.intValue();
        }

        assertEquals(15, result);
    }
}
