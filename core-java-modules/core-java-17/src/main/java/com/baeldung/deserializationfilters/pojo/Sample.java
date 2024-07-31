package com.baeldung.deserializationfilters.pojo;

public class Sample implements ContextSpecific, Comparable<Sample> {
    private static final long serialVersionUID = 1L;

    private int[] array;
    private String name;
    private NestedSample nested;

    public Sample(String name) {
        this.name = name;
    }

    public Sample(int[] array) {
        this.array = array;
    }

    public Sample(NestedSample nested) {
        this.nested = nested;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getArray() {
        return array;
    }

    public void setArray(int[] array) {
        this.array = array;
    }

    public NestedSample getNested() {
        return nested;
    }

    public void setNested(NestedSample nested) {
        this.nested = nested;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Sample o) {
        if (name == null)
            return -1;

        if (o == null || o.getName() == null)
            return 1;

        return getName().compareTo(o.getName());
    }
}
