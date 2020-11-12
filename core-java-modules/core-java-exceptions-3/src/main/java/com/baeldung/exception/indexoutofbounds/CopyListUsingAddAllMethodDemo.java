package com.baeldung.exception.indexoutofbounds;

import java.util.ArrayList;
import java.util.List;

public class CopyListUsingAddAllMethodDemo {
    static List<Integer> copyList(List<Integer> source) {
        List<Integer> destination =  new ArrayList<>();

        destination.addAll(source);

        return destination;
    }
}
