package com.baeldung.collections.comparation;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

public class LinkedListUnitTest {

    @Test
    void givenLinkedList_whenItemIsAppended_thenItCanBeRetrieved() {
        LinkedList<String> list = new LinkedList<>();
        list.addLast("Daniel");
        list.addFirst("Marko");
        assertThat(list).hasSize(2);
        assertThat(list.getLast()).isEqualTo("Daniel");
    }

    @Test
    void givenLinkedList_whenItemIsRemoved_thenListSizeIsReduced() {
        LinkedList<String> list = new LinkedList<>(Arrays.asList("Daniel", "Marko", "David"));
        list.removeFirst();
        list.removeLast();
        assertThat(list).hasSize(1);
        assertThat(list).containsExactly("Marko");
    }

    @Test
    void givenLinkedList_whenItemInserted_thenItCanBeRetrievedAndDeleted() {
        LinkedList<String> list = new LinkedList<>();
        list.push("Daniel");
        list.push("Marko");
        assertThat(list.poll()).isEqualTo("Marko");
        assertThat(list).hasSize(1);
    }

}
