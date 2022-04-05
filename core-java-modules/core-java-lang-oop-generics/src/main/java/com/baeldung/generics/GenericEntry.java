package com.baeldung.generics;

import java.io.Serializable;
import java.util.Optional;

public class GenericEntry<T> {
    private T data;
    private int rank;

    // non-generic constructor
    public GenericEntry(int rank) {
        this.rank = rank;
    }

    // generic constructor
    public GenericEntry(T data, int rank) {
        this.data = data;
        this.rank = rank;
    }

    // generic constructor with different type
    public <E extends Rankable & Serializable> GenericEntry(E element) {
        this.data = (T) element;
        this.rank = element.getRank();
    }

    // generic constructor with different type and wild card
    public GenericEntry(Optional<? extends Rankable> optional) {
        if (optional.isPresent()) {
            this.data = (T) optional.get();
            this.rank = optional.get()
                .getRank();
        }
    }

    // getters and setters
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

}
