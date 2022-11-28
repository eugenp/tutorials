package com.baeldung.collections.sorting;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

public class HashSetUnitTest {

    @Test
    void givenHashSet_whenUsingCollectionsSort_thenHashSetSorted() {
        HashSet<Integer> numberHashSet = new HashSet<>();
        numberHashSet.add(2);
        numberHashSet.add(1);
        numberHashSet.add(4);
        numberHashSet.add(3);
        // converting HashSet to arraylist
        ArrayList<Integer> arrayList = new ArrayList<>(numberHashSet);
        // sorting the list
        Collections.sort(arrayList);
        assertThat(arrayList).containsExactly(1, 2, 3, 4);
    }

    @Test
    void givenHashSet_whenUsingTreeSet_thenHashSetSorted() {
        HashSet<Integer> numberHashSet = new HashSet<>();
        numberHashSet.add(2);
        numberHashSet.add(1);
        numberHashSet.add(4);
        numberHashSet.add(3);
        // TreeSet gets the value of hashSet
        TreeSet treeSet = new TreeSet();
        treeSet.addAll(numberHashSet);
        assertThat(treeSet).containsExactly(1, 2, 3, 4);
    }

    @Test
    void givenHashSet_whenUsingStream_thenHashSetSorted() {
        HashSet<Integer> numberHashSet = new HashSet<>();
        numberHashSet.add(2);
        numberHashSet.add(1);
        numberHashSet.add(4);
        numberHashSet.add(3);
        // Sort the HashSet using stream()
        numberHashSet.stream().sorted();
        assertThat(numberHashSet).containsExactly(1, 2, 3, 4);
    }

}
