package com.baeldung.eclipsecollections;

import org.assertj.core.api.Assertions;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.tuple.Tuples;
import org.junit.Before;
import org.junit.Test;

public class ZipWithIndexUnitTest {

    MutableList<Pair<String, Integer>> expectedPairs;

    @SuppressWarnings("unchecked")
    @Before
    public void setup() {
        Pair<String, Integer> pair1 = Tuples.pair("Porsche", 0);
        Pair<String, Integer> pair2 = Tuples.pair("Volvo", 1);
        Pair<String, Integer> pair3 = Tuples.pair("Toyota", 2);
        expectedPairs = Lists.mutable.of(pair1, pair2, pair3);
    }

    @Test
    public void whenZip_thenCorrect() {
        MutableList<String> cars = FastList.newListWith("Porsche", "Volvo", "Toyota");
        MutableList<Pair<String, Integer>> pairs = cars.zipWithIndex();

        Assertions.assertThat(pairs).containsExactlyElementsOf(this.expectedPairs);
    }
}
