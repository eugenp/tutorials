package com.baeldung.set;

import java.util.HashSet;
import java.util.Set;

public class PowerSet<T> {

    public Set<Set<T>> recursivePowerSet(Set<T> set) {
        if (set.isEmpty()) {
            Set<Set<T>> ret = new HashSet<>();
            ret.add(new HashSet<>());
            return ret;
        }
        //Extract one element and get set without that element
        T element = set.iterator().next();
        Set<T> setWithoutElement = getSetWithoutElement(set, element);
        //recursively calculate the powerSet of setWithoutElement
        Set<Set<T>> powerSetSubSetWithoutElement = recursivePowerSet(setWithoutElement);
        //add the extracted element to all sets in powerSetSubSetWithoutElement
        Set<Set<T>> powerSetSubSetWithElement = addElementToAll(powerSetSubSetWithoutElement, element);
        //union of powerSetSubSetWithElement and powerSetSubSetWithoutElement will be the result
        Set<Set<T>> powerSet = new HashSet<>();
        powerSet.addAll(powerSetSubSetWithoutElement);
        powerSet.addAll(powerSetSubSetWithElement);
        return powerSet;
    }

    private Set<Set<T>> addElementToAll(Set<Set<T>> ps, T element) {
        Set<Set<T>> ret = new HashSet<>();
        for (Set<T> s : ps) {
            Set<T> subsetWithElement = new HashSet<>(s);
            subsetWithElement.add(element);
            ret.add(subsetWithElement);
        }
        return ret;
    }

    private Set<T> getSetWithoutElement(Set<T> set, T element) {
        Set<T> ret = new HashSet<>();
        for (T s : set) {
            if (!s.equals(element))
                ret.add(s);
        }
        return ret;
    }
}
