package com.baeldung.eclipsecollections;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.tuple.Tuples;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ZipTest {

    @Test
    public void whenZip_thenCorrect() {
        MutableList<String> numbers = Lists.mutable.with("1", "2", "3", "Ignored");
        MutableList<String> cars = Lists.mutable.with("Porsche", "Volvo", "Toyota");
        MutableList<Pair<String, String>> pairs = numbers.zip(cars);

        assertEquals(Tuples.pair("1", "Porsche"), pairs.get(0));
        assertEquals(Tuples.pair("2", "Volvo"), pairs.get(1));
        assertEquals(Tuples.pair("3", "Toyota"), pairs.get(2));
    }
}
