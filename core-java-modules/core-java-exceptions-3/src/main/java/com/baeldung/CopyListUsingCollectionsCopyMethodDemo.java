package com.baeldung;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CopyListUsingCollectionsCopyMethodDemo {

    static void copyList(List<Integer> source, List<Integer> destination) {
        Collections.copy(destination, source);
    }

    public static void main(String[] args) {
        List<Integer> source = Arrays.asList(11, 22, 33);
        List<Integer> destination = Arrays.asList(1, 2, 3, 4, 5);

        copyList(source, destination);

        System.out.println("copy = " + destination);
    }
}
