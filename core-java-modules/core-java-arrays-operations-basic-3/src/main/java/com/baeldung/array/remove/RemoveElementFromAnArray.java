package com.baeldung.array.remove;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;

public class RemoveElementFromAnArray {

    public int[] removeAnElementWithAGivenIndex(int[] array, int index) {
        return ArrayUtils.remove(array, index);
    }

    public int[] removeAllElementsWithGivenIndices(int[] array, int... indices) {
        return ArrayUtils.removeAll(array, indices);
    }

    public int[] removeFirstOccurrenceOfGivenElement(int[] array, int element) {
        return ArrayUtils.removeElement(array, element);
    }

    public int[] removeAllGivenElements(int[] array, int... elements) {
        return ArrayUtils.removeElements(array, elements);
    }

    public int[] removeLastElementUsingCopyOfMethod(int[] array) {
        return Arrays.copyOf(array, array.length - 1);
    }

    public int[] removeLastElementUsingCopyOfRangeMethod(int[] array) {
        return Arrays.copyOfRange(array, 0, array.length - 1);
    }

    public int[] removeLastElementUsingArrayCopyMethod(int[] array, int[] resultArray) {
        System.arraycopy(array, 0, resultArray, 0, array.length-1);
        return resultArray;
    }

    public int[] removeLastElementUsingIntStreamRange(int[] array) {
        return IntStream.range(0, array.length - 1) .map(i -> array[i]) .toArray();
    }


}
