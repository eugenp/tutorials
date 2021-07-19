package com.baeldung.array;

import java.util.ArrayList;
import java.util.Arrays;

public class AddElementToEndOfArray {

    public Integer[] addElementUsingArraysCopyOf(Integer[] srcArray, int elementToAdd) {
        Integer[] destArray = Arrays.copyOf(srcArray, srcArray.length + 1);

        destArray[destArray.length - 1] = elementToAdd;
        return destArray;
    }

    public Integer[] addElementUsingArrayList(Integer[] srcArray, int elementToAdd) {
        Integer[] destArray = new Integer[srcArray.length + 1];

        ArrayList<Integer> arrayList = new ArrayList<>(Arrays.asList(srcArray));
        arrayList.add(elementToAdd);

        return arrayList.toArray(destArray);
    }

    public Integer[] addElementUsingSystemArrayCopy(Integer[] srcArray, int elementToAdd) {
        Integer[] destArray = new Integer[srcArray.length + 1];

        System.arraycopy(srcArray, 0, destArray, 0, srcArray.length);

        destArray[destArray.length - 1] = elementToAdd;

        return destArray;
    }

}
