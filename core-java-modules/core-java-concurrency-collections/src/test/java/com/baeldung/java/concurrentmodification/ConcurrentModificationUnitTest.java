package com.baeldung.java.concurrentmodification;

import org.junit.Test;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;

public class ConcurrentModificationUnitTest {
    @Test(expected = ConcurrentModificationException.class)
    public void givenIterating_whenRemoving_thenThrowException() throws InterruptedException {

        List<Integer> integers = newArrayList(1, 2, 3);

        for (Integer integer : integers) {
            integers.remove(1);
        }
    }

    @Test
    public void givenIterating_whenUsingIteratorRemove_thenNoError() throws InterruptedException {

        List<Integer> integers = newArrayList(1, 2, 3);

        for (Iterator<Integer> iterator = integers.iterator(); iterator.hasNext(); ) {
            Integer integer = iterator.next();
            if (integer == 2) {
                iterator.remove();
            }
        }

        assertThat(integers).containsExactly(1, 3);
    }

    @Test
    public void givenIterating_whenUsingRemovalList_thenNoError() throws InterruptedException {

        List<Integer> integers = newArrayList(1, 2, 3);
        List<Integer> toRemove = newArrayList();

        for (Integer integer : integers) {
            if (integer == 2) {
                toRemove.add(integer);
            }
        }
        integers.removeAll(toRemove);

        assertThat(integers).containsExactly(1, 3);
    }

    @Test
    public void whenUsingRemoveIf_thenRemoveElements() throws InterruptedException {

        Collection<Integer> integers = newArrayList(1, 2, 3);

        integers.removeIf(i -> i == 2);

        assertThat(integers).containsExactly(1, 3);
    }

    @Test
    public void whenUsingStream_thenRemoveElements() {
        Collection<Integer> integers = newArrayList(1, 2, 3);

        List<String> collected = integers
          .stream()
          .filter(i -> i != 2)
          .map(Object::toString)
          .collect(toList());

        assertThat(collected).containsExactly("1", "3");
    }

}
