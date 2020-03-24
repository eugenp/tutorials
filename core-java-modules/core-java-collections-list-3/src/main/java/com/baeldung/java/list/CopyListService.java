package com.baeldung.java.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CopyListService {

    public List<Flower> copyListByConstructor(List<Flower> source) {
        return new ArrayList<Flower>(source);
    }

    public List<Flower> copyListByConstructorAndEditOneFlowerInTheNewList(List<Flower> source) {
        List<Flower> flowers = new ArrayList<>(source);
        if(flowers.size() > 0) {
            flowers.get(0).setPetals(flowers.get(0).getPetals() * 3);
        }

        return flowers;
    }

    public List<Flower> copyListByAddAllMethod(List<Flower> source) {
        List<Flower> flowers = new ArrayList<>();
        flowers.addAll(source);
        return flowers;
    }

    public List<Flower> copyListByAddAllMethodAndEditOneFlowerInTheNewList(List<Flower> source) {
        List<Flower> flowers = new ArrayList<>();
        flowers.addAll(source);

        if(flowers.size() > 0) {
            flowers.get(0).setPetals(flowers.get(0).getPetals() * 3);
        }

        return flowers;
    }

    public List<Integer> copyListByCopyMethod(List<Integer> source, List<Integer> dest) {
        Collections.copy(dest, source);
        return dest;
    }

    public List<Flower> copyListByStream(List<Flower> source) {
        return source.stream().collect(Collectors.toList());
    }

    public List<Flower> copyListByStreamAndSkipFirstElement(List<Flower> source) {
        return source.stream().skip(1).collect(Collectors.toList());
    }

    public List<Flower> copyListByStreamWithFilter(List<Flower> source, Integer moreThanPetals) {
        return source.stream().filter(f -> f.getPetals() > moreThanPetals).collect(Collectors.toList());
    }

    public List<Flower> copyListByStreamWithOptional(List<Flower> source) {
        return Optional.ofNullable(source)
                 .map(List::stream)
                 .orElseGet(Stream::empty)
                 .collect(Collectors.toList());
    }

    public List<Flower> copyListByStreamWithOptionalAndSkip(List<Flower> source) {
        return Optional.ofNullable(source)
                .map(List::stream)
                .orElseGet(Stream::empty)
                .skip(1)
                .collect(Collectors.toList());
    }
}
