package com.baeldung.set;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PowerSet<T> {

    private Map<Integer, T> map = new HashMap<>();

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

    public Set<Set<T>> recursivePowerSetIndexRepresentation(Collection<T> set) {
        initializeMap(set);
        Set<Set<Integer>> powerSetBoolean = recursivePowerSetIndexRepresentation(0, set.size());
        return unMapIndex(powerSetBoolean);
    }


    private Set<Set<Integer>> recursivePowerSetIndexRepresentation(int idx, int n) {
        if (idx == n) {
            Set<Set<Integer>> empty = new HashSet<>();
            empty.add(new HashSet<>());
            return empty;
        }
        Set<Set<Integer>> powerSetSubset = recursivePowerSetIndexRepresentation(idx + 1, n);
        Set<Set<Integer>> powerSet = new HashSet<>(powerSetSubset);
        for (Set<Integer> s : powerSetSubset) {
            HashSet<Integer> subSetIdxInclusive = new HashSet<>(s);
            subSetIdxInclusive.add(idx);
            powerSet.add(subSetIdxInclusive);
        }
        return powerSet;
    }

    public Set<Set<T>> recursivePowerSetBooleanRepresentation(Collection<T> set) {
        initializeMap(set);
        Set<List<Boolean>> powerSetBoolean = recursivePowerSetBooleanRepresentation(0, set.size());
        return unMapBoolean(powerSetBoolean);
    }

    private Set<List<Boolean>> recursivePowerSetBooleanRepresentation(int idx, int n) {
        if (idx == n) {
            Set<List<Boolean>> powerSetOfEmptySet = new HashSet<>();
            powerSetOfEmptySet.add(Arrays.asList(new Boolean[n]));
            return powerSetOfEmptySet;
        }
        Set<List<Boolean>> powerSetSubset = recursivePowerSetBooleanRepresentation(idx + 1, n);
        Set<List<Boolean>> powerSet = new HashSet<>();
        for (List<Boolean> s : powerSetSubset) {
            List<Boolean> subSetIdxExclusive = new ArrayList<>(s);
            subSetIdxExclusive.set(idx, false);
            powerSet.add(subSetIdxExclusive);
            List<Boolean> subSetIdxInclusive = new ArrayList<>(s);
            subSetIdxInclusive.set(idx, true);
            powerSet.add(subSetIdxInclusive);
        }
        return powerSet;
    }


    private void initializeMap(Collection<T> collection) {
        int mapId = 0;
        for (T c : collection)
            map.put(mapId++, c);
    }

    private Set<Set<T>> unMapIndex(Set<Set<Integer>> sets) {
        Set<Set<T>> ret = new HashSet<>();
        for (Set<Integer> s : sets) {
            HashSet<T> subset = new HashSet<>();
            for (Integer i : s)
                subset.add(map.get(i));
            ret.add(subset);
        }
        return ret;
    }

    private Set<Set<T>> unMapBoolean(Collection<List<Boolean>> sets) {
        Set<Set<T>> ret = new HashSet<>();
        for (List<Boolean> s : sets) {
            HashSet<T> subset = new HashSet<>();
            for (int i = 0; i < s.size(); i++)
                if (s.get(i))
                    subset.add(map.get(i));
            ret.add(subset);
        }
        return ret;
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
