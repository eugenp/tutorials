package com.baeldung.ddd.order.doubledispatch.visitor;

public interface Visitable<V> {
    void accept(V visitor);
}
