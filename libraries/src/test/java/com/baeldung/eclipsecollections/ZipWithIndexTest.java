package com.baeldung.eclipsecollections;

import static org.junit.Assert.assertEquals;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.tuple.Tuples;
import org.junit.Test;

public class ZipWithIndexTest {

    @Test
    public void whenZip_thenCorrect() {
        MutableList<String> cars = FastList.newListWith("Porsche", "Volvo", "Toyota");
        MutableList<Pair<String, Integer>> pairs = cars.zipWithIndex();

        assertEquals(Tuples.pair("Porsche", 0), pairs.get(0));
        assertEquals(Tuples.pair("Volvo", 1), pairs.get(1));
        assertEquals(Tuples.pair("Toyota", 2), pairs.get(2));
    }
}
