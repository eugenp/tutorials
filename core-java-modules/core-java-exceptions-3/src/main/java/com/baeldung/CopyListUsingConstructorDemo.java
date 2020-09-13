package com.baeldung;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CopyListUsingConstructorDemo {

    static List<Integer> copyList(List<Integer> source) {

        return new ArrayList<>(source);
    }

    public static void main(String[] args) {
        List<Integer> source = Arrays.asList(11, 22, 33);

        List<Integer> destination = copyList(source);

        System.out.println("copy = " + destination);
    }
}
