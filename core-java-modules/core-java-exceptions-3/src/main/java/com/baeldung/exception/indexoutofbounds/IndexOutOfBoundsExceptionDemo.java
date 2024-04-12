package com.baeldung.exception.indexoutofbounds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IndexOutOfBoundsExceptionDemo {
    static List<Integer> copyList(List<Integer> source) {
        List<Integer> destination = new ArrayList<>(source.size());
        Collections.copy(destination, source);
        return destination;
    }
}
