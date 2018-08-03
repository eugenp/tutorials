package com.baeldung.array;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RemoveFirstElementUnitTest {

    @Test
    public void givenStringArray_whenRemovingFirstElement_thenArrayIsSmallerAndElementRemoved() {
        String[] stringArray = {"foo", "bar", "baz"};

        String[] modifiedArray = Arrays.copyOfRange(stringArray, 1, stringArray.length);

        assertThat(modifiedArray.length).isEqualTo(2);
        assertThat(modifiedArray[0]).isEqualTo("bar");
    }

    @Test
    public void givenArrayList_whenRemovingFirstElement_thenListSmallerAndElementRemoved() {
        List<String> stringList = new ArrayList<>(Arrays.asList("foo", "bar", "baz"));
        stringList.remove(0);

        assertThat(stringList.size()).isEqualTo(2);
        assertThat(stringList.get(0)).isEqualTo("bar");
    }

    @Test
    public void givenLinkedList_whenRemovingFirstElement_thenListSmallerAndElementRemoved() {
        List<String> stringList = new LinkedList<>(Arrays.asList("foo", "bar", "baz"));
        stringList.remove(0);

        assertThat(stringList.size()).isEqualTo(2);
        assertThat(stringList.get(0)).isEqualTo("bar");
    }

}
