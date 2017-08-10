package com.baeldung.eclipsecollections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.partition.list.PartitionMutableList;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.junit.Before;
import org.junit.Test;

public class PartitionPatternTest {

    MutableList<Integer> list;

    @Before
    public void getList() {
        this.list = new FastList<>();
        list.add(1);
        list.add(8);
        list.add(5);
        list.add(41);
        list.add(31);
        list.add(17);
        list.add(23);
        list.add(38);
    }

    @SuppressWarnings({ "unused" })
    @Test
    public void whenAnySatisfiesCondition_thenCorrect() {
        MutableList<Integer> numbers = list;
        PartitionMutableList<Integer> partitionedFolks = numbers.partition(new Predicate<Integer>() {

            /**
             * 
             */
            private static final long serialVersionUID = -1551138743683678406L;

            public boolean accept(Integer each) {
                return each > 30;
            }
        });
        MutableList<Integer> greaterThanThirty = partitionedFolks.getSelected()
            .sortThis();
        MutableList<Integer> smallerThanThirty = partitionedFolks.getRejected()
            .sortThis();

        assertEquals(1, (int) smallerThanThirty.getFirst());
        assertTrue(smallerThanThirty.anySatisfy(Predicates.equal(5)));
        assertTrue(smallerThanThirty.anySatisfy(Predicates.equal(8)));
        assertTrue(smallerThanThirty.anySatisfy(Predicates.equal(17)));
        assertTrue(smallerThanThirty.anySatisfy(Predicates.equal(23)));

        assertTrue(greaterThanThirty.anySatisfy(Predicates.equal(31)));
        assertTrue(greaterThanThirty.anySatisfy(Predicates.equal(38)));
        assertTrue(greaterThanThirty.anySatisfy(Predicates.equal(41)));
    }
}
