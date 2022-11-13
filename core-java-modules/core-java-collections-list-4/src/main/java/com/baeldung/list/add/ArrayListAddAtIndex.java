package com.baeldung.list.add;


import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Integer> integers = new ArrayList<>();
        // add some elements
        integers.add(5);
        integers.add(6);
        integers.add(7);
        integers.add(8);
        // print the array list
        System.out.println(integers);
        // add an element at a specific index
        integers.add(2, 9);
        // print the array list
        System.out.println(integers);
        // sort the array list
        integers.sort(Integer::compareTo);
        // print the array list
        System.out.println(integers);
    }
}