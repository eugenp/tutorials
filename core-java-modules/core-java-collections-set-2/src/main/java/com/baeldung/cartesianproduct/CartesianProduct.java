package com.baeldung.cartesianproduct;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Sets;

public class CartesianProduct {
    public List<List<Object>> getCartesianProductIterative(List<List<Object>> sets) {
        List<List<Object>> result = new ArrayList<>();
        if(sets == null || sets.isEmpty()) {
            return result;
        }
        int totalSets = sets.size();
        int totalCombinations = 1 << totalSets;
        for(int i = 0; i < totalCombinations; i++) {
            List<Object> combination = new ArrayList<>();
            for(int j = 0; j < totalSets; j++) {
                if (((i >> j) & 1) == 1) {
                    combination.add(sets.get(j).get(0));
                } else {
                    combination.add(sets.get(j).get(1));
                }
            }
            result.add(combination);
        }
        return result;
    }

    public List<List<Object>> getCartesianProductRecursive(List<List<Object>> sets) {
        List<List<Object>> result = new ArrayList<>();
        getCartesianProductRecursiveHelper(sets, 0, new ArrayList<>(), result);
        return result;
    }

    private void getCartesianProductRecursiveHelper(List<List<Object>> sets, int index, List<Object> current, List<List<Object>> result) {
        if(index == sets.size()) {
            result.add(new ArrayList<>(current));
            return;
        }
        List<Object> currentSet = sets.get(index);
        for(Object element: currentSet) {
            current.add(element);
            getCartesianProductRecursiveHelper(sets, index+1, current, result);
            current.remove(current.size() - 1);
        }
    }

    public List<List<Object>> getCartesianProductUsingStreams(List<List<Object>> sets) {
        return cartesianProduct(sets,0).collect(Collectors.toList());
    }

    public Stream<List<Object>> cartesianProduct(List<List<Object>> sets, int index) {
        if(index == sets.size()) {
            List<Object> emptyList = new ArrayList<>();
            return Stream.of(emptyList);
        }
        List<Object> currentSet = sets.get(index);
        return currentSet.stream().flatMap(element -> cartesianProduct(sets, index+1)
            .map(list -> {
                List<Object> newList = new ArrayList<>(list);
                newList.add(0, element); return newList;
            }));
    }

    public List<List<Object>> getCartesianProductUsingGuava(List<Set<Object>> sets) {
        Set<List<Object>> cartesianProduct = Sets.cartesianProduct(sets);
        List<List<Object>> cartesianList = new ArrayList<>(cartesianProduct);
        return cartesianList;
    }

}
