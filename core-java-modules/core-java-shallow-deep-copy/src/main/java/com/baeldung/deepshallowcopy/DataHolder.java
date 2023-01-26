package com.baeldung.deepshallowcopy;

import java.io.Serializable;
import java.util.Arrays;

class DataHolder implements Cloneable, Serializable {

    private int[] marks;

    public DataHolder() {
    }

    public DataHolder(int[] marks) {
        this.marks = marks;
    }

    public void setMarks(int[] marks) {
        this.marks = marks;
    }

    public int[] getMarks() {
        return marks;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return (DataHolder) super.clone();
    }

    @Override
    public String toString() {
        return "DataHolder{" + "marks=" + Arrays.toString(marks) + '}';
    }

}
