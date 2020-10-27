package com.baeldung.listcapacityvsarraysize;

import java.util.ArrayList;

public class ArrayListCreator<T> {
    public ArrayList<T> createArrayList(int capacity) {
        return new ArrayList<>(capacity);
    }
}
