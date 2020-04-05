package com.baeldung.arraylist.operations;

import java.util.ArrayList;

public class ArrayListOperations {

    public static Integer getAnIntegerElement(ArrayList<Integer> anArrayList, int index) {
        return anArrayList.get(index);
    }

    public static void modifyAnIntegerElement(ArrayList<Integer> anArrayList, int index, Integer newElement) {
        anArrayList.set(index, newElement);
    }

    public static void appendAnIntegerElement(ArrayList<Integer> anArrayList, Integer newElement) {
        anArrayList.add(newElement);
    }

    public static void insertAnIntegerElementAtIndex(ArrayList<Integer> anArrayList, int index, Integer newElement) {
        anArrayList.add(index, newElement);
    }

}
