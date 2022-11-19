package com.baeldung.objectcopy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeepCopy {
    List<Integer> list;

    public DeepCopy(List<Integer> list) {
        this.list = new ArrayList<>(); // making a deep copy
        this.list.addAll(list);
    }

    public static void main(String[] args) {
        ArrayList<Integer> input = new ArrayList<>(Arrays.asList(1, 2, 3, 4));

        DeepCopy deepCopy = new DeepCopy(input);
        System.out.println(deepCopy.list); // [1, 2, 3, 4]
        input.add(5); //modifying the actual input
        System.out.println(deepCopy.list);// still prints [1, 2, 3, 4]
    }
}
