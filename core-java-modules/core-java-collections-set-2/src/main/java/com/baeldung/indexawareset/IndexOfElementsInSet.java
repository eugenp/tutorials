package com.baeldung.indexawareset;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class IndexOfElementsInSet<E> {
    public int getIndexOf(Set<E> set, E element) {
        List<E> arrayList = new ArrayList<>();
        arrayList.addAll(set);
        return arrayList.indexOf(element);
    }
}
