package com.baeldung.streams.conversion;

import java.util.Enumeration;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;

public class EnumerationSpliterator<T> extends AbstractSpliterator<T> {

    private final Enumeration<T> enumeration;

    public EnumerationSpliterator(long est, int additionalCharacteristics, Enumeration<T> enumeration) {
        super(est, additionalCharacteristics);
        this.enumeration = enumeration;
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        if (enumeration.hasMoreElements()) {
            action.accept(enumeration.nextElement());
            return true;
        }
        return false;
    }

    @Override
    public void forEachRemaining(Consumer<? super T> action) {
        while (enumeration.hasMoreElements())
            action.accept(enumeration.nextElement());
    }
}
