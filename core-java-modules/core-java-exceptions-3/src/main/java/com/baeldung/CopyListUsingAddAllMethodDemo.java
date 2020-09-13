package com.baeldung;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CopyListUsingAddAllMethodDemo {

    static List<Integer> copyList(List<Integer> source) {
        List<Integer> destination =  new ArrayList<>();

        destination.addAll(source);

        return destination;
    }

    public static void main(String[] args) {
        List<Integer> source = Arrays.asList(11, 22, 33);

        List<Integer> destination = copyList(source);

        System.out.println("copy = " + destination);
    }
}
