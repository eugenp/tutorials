package org.baeldubng.java.entity;

public class Fruit implements Comparable<Fruit> {

    private int size;
    private String name;

    public Fruit(final int size, final String name) {

        this.size = size;
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof Fruit) {
            final Fruit that = (Fruit) o;
            return name.equals(that.name) && size == that.size;
        } else
            return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode() * 29 + size;
    }

    @Override
    public int compareTo(final Fruit that) {
        return size < that.size ? -1 : size == that.size ? 0 : 1;
    }

    @Override
    public String toString() {
        return new Integer(size).toString();
    }

}
