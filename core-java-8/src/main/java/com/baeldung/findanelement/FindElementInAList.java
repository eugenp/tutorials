package com.baeldung.findanelement;

import java.util.List;
import java.util.ListIterator;
import org.apache.commons.collections4.IterableUtils;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class FindElementInAList<T> {

    public T findUsingIndexOf(T element, List<T> list) {
        int index = list.indexOf(element);
        if (index >= 0) {
            return element;
        }
        return null;
    }

    public boolean findUsingListIterator(T element, List<T> list) {
        ListIterator<T> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            T elementFromList = listIterator.next();
            if (elementFromList.equals(element)) {
                return true;
            }
        }
        return false;
    }

    public boolean findUsingEnhancedForLoop(T element, List<T> list) {
        for (T elementFromList : list) {
            if (element.equals(elementFromList)) {
                return true;
            }
        }
        return false;
    }

    public T findUsingStream(T element, List<T> list) {
        return list.stream()
            .filter(integer -> integer.equals(element))
            .findFirst()
            .orElse(null);
    }

    public T findUsingParallelStream(T element, List<T> list) {
        return list.parallelStream()
            .filter(integer -> integer.equals(element))
            .findAny()
            .orElse(null);
    }

    public T findUsingGuava(T element, List<T> list) {
        T foundElement = Iterables.tryFind(list, new Predicate<T>() {
            public boolean apply(T input) {
                return element.equals(input);
            }
        }).orNull();
        return foundElement;
    }

    public T findUsingApacheCommon(T element, List<T> list) {
        T foundElement = IterableUtils.find(list, new org.apache.commons.collections4.Predicate<T>() {
            public boolean evaluate(T input) {
                return element.equals(input);
            }
        });
        return foundElement;
    }

}