package com.baeldung.generics;

import java.io.Serializable;

public class Entry {
    private String data;
    private int rank;

    // non-generic constructor
    public Entry(String data, int rank) {
        this.data = data;
        this.rank = rank;
    }

    // generic constructor
    public <E extends Rankable & Serializable> Entry(E element) {
        this.data = element.toString();
        this.rank = element.getRank();
    }

    // getters and setters
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

}
