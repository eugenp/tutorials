package com.baeldung;

import java.util.ArrayList;
import java.util.List;

public class ShallowCopyObject implements Cloneable {

    ArrayList<Integer> integers;

    public ShallowCopyObject clone() {
        try {
            return (ShallowCopyObject) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public List<Integer> getIntegers() {
        return integers;
    }

    public ShallowCopyObject(ArrayList<Integer> integers) {
        this.integers = integers;
    }

}

