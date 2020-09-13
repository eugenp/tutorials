package com.baeldung.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This example produces an IndexOutOfBoundsException, when we try to copy a list using the Collections.copy method.
 * As the destination list doesn't have enough space/size to copy elements from source list.
 */
public class IndexOutOfBoundsException {

    static List<Integer> copyList(List<Integer> source) {
        List<Integer> destination = new ArrayList<>(source.size());
        Collections.copy(destination, source);
        return destination;
    }

    public static void main(String[] args) {
        List<Integer> source =  Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> copy = copyList(source);

        System.out.println("copy = " + copy);
    }

}
