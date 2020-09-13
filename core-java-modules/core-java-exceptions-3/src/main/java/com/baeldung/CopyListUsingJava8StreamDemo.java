package com.baeldung;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CopyListUsingJava8StreamDemo {

    static List<Integer> copyList(List<Integer> source) {
        return source
                .stream()
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<Integer> source = Arrays.asList(11, 22, 33);

        List<Integer> destination = copyList(source);

        System.out.println("copy = " + destination);
    }
}
