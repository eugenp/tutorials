package com.baeldung.indexoutofbounds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This example produces an IndexOutOfBoundsExceptionDemo, when we try to copy a list using the Collections.copy method.
 * As the destination list doesn't have enough space/size to copy elements from source list.
 */
public class IndexOutOfBoundsExceptionDemo {
    static List<Integer> copyList(List<Integer> source) {
        List<Integer> destination = new ArrayList<>(source.size());
        Collections.copy(destination, source);
        return destination;
    }
}
