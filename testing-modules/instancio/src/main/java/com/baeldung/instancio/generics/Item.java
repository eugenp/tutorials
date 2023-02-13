package com.baeldung.instancio.generics;

import com.baeldung.instancio.abstracttype.AbstractItem;

public class Item<T> implements AbstractItem<T> {

    private T value;

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("Item[value=%s]", value);
    }
}