package org.baeldung.java.entity;

public class Apple implements Comparable<Apple> {

    private int weight;
    private String name;

    public Apple(final int size, final String name) {

        weight = size;
        this.name = name;
    }

    public int getSize() {
        return weight;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof Apple) {
            final Apple that = (Apple) o;
            return weight == that.weight;
        } else
            return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode() * 29 + weight;
    }

    @Override
    public int compareTo(final Apple that) {
        return weight < that.weight ? -1 : weight == that.weight ? 0 : 1;
    }

    @Override
    public String toString() {
        return new Integer(weight).toString();
    }

}
