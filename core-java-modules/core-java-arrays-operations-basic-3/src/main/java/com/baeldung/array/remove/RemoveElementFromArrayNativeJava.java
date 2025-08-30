package com.baeldung.array.remove;

import java.util.stream.IntStream;

public class RemoveElementFromArrayNativeJava {
    static int[] removeWithLoop(int[] source, int index) {
        int[] result = new int[source.length - 1];
        for (int i = 0, j = 0; i < source.length; i++) {
            if (i == index) {
                continue; // skip the element we are removing 
            }
            result[j++] = source[i];
        }
        return result;
    }

    static int[] removeWithArrayCopy(int[] source, int index) {
        int elementsBefore = index;
        int elementsAfter = source.length - index - 1;
        int[] result = new int[source.length - 1];
        System.arraycopy(source, 0, result, 0, elementsBefore);
        System.arraycopy(source, index + 1, result, index, elementsAfter);
        return result;
    }

    static int[] removeWithStream(int[] source, int index) {
        return IntStream.range(0, source.length)
                .filter(i -> i != index)
                .map(i -> source[i])
                .toArray();
    }
}
