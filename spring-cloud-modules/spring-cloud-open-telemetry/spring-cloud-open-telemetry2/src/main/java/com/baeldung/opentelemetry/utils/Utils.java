package com.baeldung.opentelemetry.utils;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * @author : Nam Thang
 * @since : 04/05/2023, Thu
 **/
public class Utils {

    public static <E> Iterable<E> iterable(final Enumeration<E> enumeration) {
        if (enumeration == null) {
            throw new NullPointerException();
        }
        return () -> new Iterator<E>() {
            public boolean hasNext() {
                return enumeration.hasMoreElements();
            }
            public E next() {
                return enumeration.nextElement();
            }
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
