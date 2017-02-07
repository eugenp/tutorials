package com.baeldung.java.concurrentmodificationexception;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;

public class ConcurrentModificationUnitTest {
    @Test(expected = ConcurrentModificationException.class)
    public void givenIterating_whenRemoving_thenThrowException() throws InterruptedException {

        ArrayList<Integer> integers = newArrayList(1, 2, 3);

        for (Integer integer : integers) {
            integers.remove(1);
        }
    }

    @Test
    public void givenIterating_whenUsingIteratorRemove_thenDontError() throws InterruptedException {

        ArrayList<Integer> integers = newArrayList(1, 2, 3);

        for (Iterator<Integer> iterator = integers.iterator(); iterator.hasNext();) {
            Integer integer = iterator.next();
            if(integer == 2) {
                iterator.remove();
            }
        }

        assertThat(integers).containsExactly(1, 3);
    }

    @Test
    public void givenIterating_whenUsingRemovalList_thenDontError() throws InterruptedException {

        ArrayList<Integer> integers = newArrayList(1, 2, 3);
        ArrayList<Integer> toRemove = newArrayList();

        for (Integer integer : integers) {
            if(integer == 2) {
                toRemove.add(integer);
            }
        }
        integers.removeAll(toRemove);

        assertThat(integers).containsExactly(1, 3);
    }

    @Test
    public void whenUsingRemoveIf_thenRemoveElements() throws InterruptedException {

        ArrayList<Integer> integers = newArrayList(1, 2, 3);

        integers.removeIf((i) -> i == 2);

        assertThat(integers).containsExactly(1, 3);
    }
}
