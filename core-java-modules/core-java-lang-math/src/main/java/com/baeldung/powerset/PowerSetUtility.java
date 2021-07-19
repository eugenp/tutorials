package com.baeldung.powerset;

import javax.annotation.Nullable;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class PowerSetUtility<T> {

    private Map<T, Integer> map = new HashMap<>();
    private List<T> reverseMap = new ArrayList<>();

    //Lazy Load PowerSet class
    private static class PowerSet<E> extends AbstractSet<Set<E>> {
        private Map<E, Integer> map = new HashMap<>();
        private List<E> reverseMap = new ArrayList<>();
        private Set<E> set;

        public PowerSet(Set<E> set) {
            this.set = set;
            initializeMap();
        }

        abstract class ListIterator<K> implements Iterator<K> {

            protected int position = 0;
            private int size;

            public ListIterator(int size) {
                this.size = size;
            }

            @Override
            public boolean hasNext() {
                return position < size;
            }
        }

        static class Subset<E> extends AbstractSet<E> {
            private Map<E, Integer> map;
            private List<E> reverseMap;
            private int mask;

            public Subset(Map<E, Integer> map, List<E> reverseMap, int mask) {
                this.map = map;
                this.reverseMap = reverseMap;
                this.mask = mask;
            }

            @Override
            public Iterator<E> iterator() {
                return new Iterator<E>() {
                    int remainingSetBits = mask;

                    @Override
                    public boolean hasNext() {
                        return remainingSetBits != 0;
                    }

                    @Override
                    public E next() {
                        int index = Integer.numberOfTrailingZeros(remainingSetBits);
                        if (index == 32) {
                            throw new NoSuchElementException();
                        }
                        remainingSetBits &= ~(1 << index);
                        return reverseMap.get(index);
                    }
                };
            }

            @Override
            public int size() {
                return Integer.bitCount(mask);
            }

            @Override
            public boolean contains(@Nullable Object o) {
                Integer index = map.get(o);
                return index != null && (mask & (1 << index)) != 0;
            }
        }

        @Override
        public Iterator<Set<E>> iterator() {
            return new ListIterator<Set<E>>(this.size()) {
                @Override
                public Set<E> next() {
                    return new Subset<>(map, reverseMap, position++);
                }
            };
        }

        @Override
        public int size() {
            return (1 << this.set.size());
        }

        @Override
        public boolean contains(@Nullable Object obj) {
            if (obj instanceof Set) {
                Set<?> set = (Set<?>) obj;
                return reverseMap.containsAll(set);
            }
            return false;
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj instanceof PowerSet) {
                PowerSet<?> that = (PowerSet<?>) obj;
                return set.equals(that.set);//Set equals check to have the same element regardless of the order of the items
            }
            return super.equals(obj);
        }


        private void initializeMap() {
            int mapId = 0;
            for (E c : this.set) {
                map.put(c, mapId++);
                reverseMap.add(c);
            }
        }
    }

    public Set<Set<T>> lazyLoadPowerSet(Set<T> set) {
        return new PowerSet<>(set);
    }

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

    private List<List<Boolean>> iterativePowerSetByLoopOverNumbers(int n) {
        List<List<Boolean>> powerSet = new ArrayList<>();
        for (int i = 0; i < (1 << n); i++) {
            List<Boolean> subset = new ArrayList<>(n);
            for (int j = 0; j < n; j++)
                subset.add(((1 << j) & i) > 0);
            powerSet.add(subset);
        }
        return powerSet;
    }

    private List<List<Boolean>> iterativePowerSetByLoopOverNumbersWithMinimalChange(int n) {
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
        List<List<Boolean>> sets = iterativePowerSetByLoopOverNumbers(set.size());
        return unMapListBinary(sets);
    }

    public List<List<T>> iterativePowerSetByLoopOverNumbersMinimalChange(Set<T> set) {
        initializeMap(set);
        List<List<Boolean>> sets = iterativePowerSetByLoopOverNumbersWithMinimalChange(set.size());
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
        for (T c : collection) {
            map.put(c, mapId++);
            reverseMap.add(c);
        }
    }

    private Set<Set<T>> unMapIndex(Set<Set<Integer>> sets) {
        Set<Set<T>> ret = new HashSet<>();
        for (Set<Integer> s : sets) {
            HashSet<T> subset = new HashSet<>();
            for (Integer i : s)
                subset.add(reverseMap.get(i));
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
                    subset.add(reverseMap.get(i));
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
                    subset.add(reverseMap.get(i));
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
