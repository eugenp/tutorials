package com.baeldung.springaop.unittest;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ArraySorting {

    public <T extends Comparable<? super T>> void sort(List<T> list) {
        Collections.sort(list);
    }

}
