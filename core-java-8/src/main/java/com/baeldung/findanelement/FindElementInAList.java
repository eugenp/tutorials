package com.baeldung.findanelement;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import org.apache.commons.collections4.IterableUtils;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class FindElementInAList<T> {

    public T findUsingIndexOf(T element, List<T> listOfIntegers) {
        int index = listOfIntegers.indexOf(element);
        if (index >= 0) {
            return element;
        }
        return null;
    }

    public T findUsingListIterator(T element, List<T> listOfIntegers) {
        ListIterator<T> listIterator = listOfIntegers.listIterator();
        while (listIterator.hasNext()) {
            T elementFromList = listIterator.next();
            if (elementFromList.equals(element)) {
                return element;
            }
        }
        return null;
    }

    public T findUsingEnhancedForLoop(T element, List<T> listOfIntegers) {
        for (T elementFromList : listOfIntegers) {
            if (element.equals(elementFromList)) {
                return elementFromList;
            }
        }
        return null;
    }

    public T findUsingStream(T element, List<T> listOfIntegers) {
        Optional<T> foundElement = listOfIntegers.stream()
            .filter(integer -> integer.equals(element))
            .findFirst();
        return foundElement.isPresent() ? foundElement.get() : null;
    }

    public T findUsingParallelStream(T element, List<T> listOfIntegers) {
        Optional<T> foundElement = listOfIntegers.parallelStream()
            .filter(integer -> integer.equals(element))
            .findAny();
        return foundElement.isPresent() ? foundElement.get() : null;
    }

    public T findUsingGuava(T element, List<T> listOfIntegers) {
        T foundElement = Iterables.tryFind(listOfIntegers, new Predicate<T>() {
            public boolean apply(T input) {
                return element.equals(input);
            }
        }).orNull();
        return foundElement;
    }

    public T findUsingApacheCommon(T element, List<T> listOfIntegers) {
        T foundElement = IterableUtils.find(listOfIntegers, new org.apache.commons.collections4.Predicate<T>() {
            public boolean evaluate(T input) {
                return element.equals(input);
            }
        });
        return foundElement;
    }

}