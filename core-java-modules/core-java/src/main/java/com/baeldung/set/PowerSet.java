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
            ret.add(set);
            return ret;
        }

        T element = set.iterator().next();
        Set<T> subSetWithoutElement = getSubSetWithoutElement(set, element);
        Set<Set<T>> powerSetSubSetWithoutElement = recursivePowerSet(subSetWithoutElement);

        Set<Set<T>> powerSetSubSetWithElement = addElementToAll(powerSetSubSetWithoutElement, element);

        Set<Set<T>> powerSet = new HashSet<>();
        powerSet.addAll(powerSetSubSetWithoutElement);
        powerSet.addAll(powerSetSubSetWithElement);
        return powerSet;
    }

    public Set<Set<T>> recursivePowerSetIndexRepresentation(Collection<T> set) {
        initializeMap(set);
        Set<Set<Integer>> powerSetIndices = recursivePowerSetIndexRepresentation(0, set.size());
        return unMapIndex(powerSetIndices);
    }

    private List<List<Boolean>> iterativePowerSetByLoopOverNumbersWithLexicographicalOrder(int n) {
        List<List<Boolean>> powerSet = new ArrayList<>();
        for (int i = 0; i < (1 << n); i++) {
            List<Boolean> subset = new ArrayList<>(n);
            for (int j = 0; j < n; j++)
                subset.add(((1 << j) & i) > 0);
            powerSet.add(subset);
        }
        return powerSet;
    }

    private List<List<Boolean>> iterativePowerSetByLoopOverNumbersWithGrayCodeOrder(int n) {
        List<List<Boolean>> powerSet = new ArrayList<>();
        for (int i = 0; i < (1 << n); i++) {
            List<Boolean> subset = new ArrayList<>(n);
            for (int j = 0; j < n; j++) {
                int grayEquivalent = i ^ (i >> 1);
                subset.add(((1 << j) & grayEquivalent) > 0);
            }
            powerSet.add(subset);
        }
        return powerSet;
    }

    public Set<Set<T>> recursivePowerSetBinaryRepresentation(Collection<T> set) {
        initializeMap(set);
        Set<List<Boolean>> powerSetBoolean = recursivePowerSetBinaryRepresentation(0, set.size());
        return unMapBinary(powerSetBoolean);
    }

    public List<List<T>> iterativePowerSetByLoopOverNumbers(Set<T> set) {
        initializeMap(set);
                List<List<Boolean>> sets = iterativePowerSetByLoopOverNumbersWithLexicographicalOrder(set.size());
        return unMapListBinary(sets);
    }

    public List<List<T>> iterativePowerSetByLoopOverNumbersMinimalChange(Set<T> set) {
        initializeMap(set);
        List<List<Boolean>> sets = iterativePowerSetByLoopOverNumbersWithGrayCodeOrder(set.size());
        return unMapListBinary(sets);
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

    private Set<List<Boolean>> recursivePowerSetBinaryRepresentation(int idx, int n) {
        if (idx == n) {
            Set<List<Boolean>> powerSetOfEmptySet = new HashSet<>();
            powerSetOfEmptySet.add(Arrays.asList(new Boolean[n]));
            return powerSetOfEmptySet;
        }
        Set<List<Boolean>> powerSetSubset = recursivePowerSetBinaryRepresentation(idx + 1, n);
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

    private Set<Set<T>> unMapBinary(Collection<List<Boolean>> sets) {
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

    private List<List<T>> unMapListBinary(Collection<List<Boolean>> sets) {
        List<List<T>> ret = new ArrayList<>();
        for (List<Boolean> s : sets) {
            List<T> subset = new ArrayList<>();
            for (int i = 0; i < s.size(); i++)
                if (s.get(i))
                    subset.add(map.get(i));
            ret.add(subset);
        }
        return ret;
    }

    private Set<Set<T>> addElementToAll(Set<Set<T>> powerSetSubSetWithoutElement, T element) {
        Set<Set<T>> powerSetSubSetWithElement = new HashSet<>();
        for (Set<T> subsetWithoutElement : powerSetSubSetWithoutElement) {
            Set<T> subsetWithElement = new HashSet<>(subsetWithoutElement);
            subsetWithElement.add(element);
            powerSetSubSetWithElement.add(subsetWithElement);
        }
        return powerSetSubSetWithElement;
    }

    private Set<T> getSubSetWithoutElement(Set<T> set, T element) {
        Set<T> subsetWithoutElement = new HashSet<>();
        for (T s : set) {
            if (!s.equals(element))
                subsetWithoutElement.add(s);
        }
        return subsetWithoutElement;
    }
}
