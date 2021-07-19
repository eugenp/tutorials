package com.baeldung.array;

import org.apache.commons.lang3.ArrayUtils;

public class RemoveElementFromAnArray {

    public int[] removeAnElementWithAGivenIndex(int[] array, int index) {
        return ArrayUtils.remove(array, index);
    }

    public int[] removeAllElementsWithGivenIndices(int[] array, int... indicies) {
        return ArrayUtils.removeAll(array, indicies);
    }

    public int[] removeFirstOccurrenceOfGivenElement(int[] array, int element) {
        return ArrayUtils.removeElement(array, element);
    }

    public int[] removeAllGivenElements(int[] array, int... elements) {
        return ArrayUtils.removeElements(array, elements);
    }

    public int[] removeAllOccurrencesOfAGivenElement(int[] array, int element) {
        return ArrayUtils.removeAllOccurences(array, element);
    }

}
