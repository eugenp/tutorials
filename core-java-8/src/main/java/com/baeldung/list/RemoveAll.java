package com.baeldung.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RemoveAll {

    static void removeWithStandardForLoopUsingElement(List<Integer> list, int element) {
        for (int i = 0; i < list.size(); i++) {
            if (Objects.equals(element, list.get(i))) {
                list.remove(element);
            }
        }
    }

    static void removeWithStandardForLoopUsingIndex(List<Integer> list, int element) {
        for (int i = 0; i < list.size(); i++) {
            if (Objects.equals(element, list.get(i))) {
                list.remove(i);
            }
        }
    }

    static void removeWithForLoopDecrementOnRemove(List<Integer> list, int element) {
        for (int i = 0; i < list.size(); i++) {
            if (Objects.equals(element, list.get(i))) {
                list.remove(i);
                i--;
            }
        }
    }

    static void removeWithForLoopIncrementIfRemains(List<Integer> list, int element) {
        for (int i = 0; i < list.size();) {
            if (Objects.equals(element, list.get(i))) {
                list.remove(i);
            } else {
                i++;
            }
        }
    }

    static void removeWithForEachLoop(List<Integer> list, int element) {
        for (Integer number : list) {
            if (Objects.equals(number, element)) {
                list.remove(number);
            }
        }
    }

    static void removeWithIterator(List<Integer> list, int element) {
        for (Iterator<Integer> i = list.iterator(); i.hasNext();) {
            Integer number = i.next();
            if (Objects.equals(number, element)) {
                i.remove();
            }
        }
    }

    static List<Integer> removeWithCollectingAndReturningRemainingElements(List<Integer> list, int element) {
        List<Integer> remainingElements = new ArrayList<>();
        for (Integer number : list) {
            if (!Objects.equals(number, element)) {
                remainingElements.add(number);
            }
        }
        return remainingElements;
    }

    static void removeWithCollectingRemainingElementsAndAddingToOriginalList(List<Integer> list, int element) {
        List<Integer> remainingElements = new ArrayList<>();
        for (Integer number : list) {
            if (!Objects.equals(number, element)) {
                remainingElements.add(number);
            }
        }

        list.clear();
        list.addAll(remainingElements);
    }

    static void removeWithCallingRemoveUntilModifies(List<Integer> list, Integer element) {
        while (list.remove(element))
            ;
    }

    static List<Integer> removeWithStreamFilter(List<Integer> list, Integer element) {
        return list.stream()
            .filter(e -> !Objects.equals(e, element))
            .collect(Collectors.toList());
    }

    static void removeWithRemoveIf(List<Integer> list, Integer element) {
        list.removeIf(n -> Objects.equals(n, element));
    }

}
