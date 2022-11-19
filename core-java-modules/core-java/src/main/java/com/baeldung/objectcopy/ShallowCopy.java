package com.baeldung.objectcopy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShallowCopy {
    List<Integer> list;

    public ShallowCopy(List<Integer> list) {
        this.list = list;
    }

    public static void main(String[] args) {
        ArrayList<Integer> input = new ArrayList<>(Arrays.asList(1, 2, 3, 4));

        ShallowCopy shallowCopy = new ShallowCopy(input);
        System.out.println(shallowCopy.list); // [1, 2, 3, 4]
        input.add(5); //modifying the actual input
        System.out.println(shallowCopy.list);// prints [1, 2, 3, 4, 5]
    }
}
