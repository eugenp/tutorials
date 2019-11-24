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
        T element = set.iterator().next();
        Set<T> subset = getSetWithoutElement(set, element);
        Set<Set<T>> powerSetSubSet = recursivePowerSet(subset);
        Set<Set<T>> powerSet = new HashSet<>(powerSetSubSet);
        for (Set<T> s : powerSetSubSet) {
            Set<T> subsetWithElement = new HashSet<>(s);
            subsetWithElement.add(element);
            powerSet.add(subsetWithElement);
        }
        return powerSet;
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
