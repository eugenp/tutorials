package com.baeldung.collections.dequestack;

import java.util.Collection;

/**
 * A simple Last-In-First-Out (LIFO) stack abstraction.
 *
 * @param <E> the type of elements held in this stack
 */
public interface LifoStack<E> extends Collection<E> {

    E peek();

    E pop();

    void push(E item);
}