package com.baeldung.stream;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.codepoetics.protonpack.Indexed;
import com.codepoetics.protonpack.StreamUtils;

import io.vavr.collection.Stream;
import one.util.streamex.EntryStream;

public class StreamIndices {

    public static List<String> getEvenIndexedStrings(String[] names) {
        List<String> evenIndexedNames = IntStream.range(0, names.length)
            .filter(i -> i % 2 == 0)
            .mapToObj(i -> names[i])
            .collect(Collectors.toList());
        return evenIndexedNames;
    }

    public List<String> getEvenIndexedStringsVersionTwo(List<String> names) {
        List<String> evenIndexedNames = EntryStream.of(names)
            .filterKeyValue((index, name) -> index % 2 == 0)
            .values()
            .toList();
        return evenIndexedNames;
    }

    public static List<Indexed<String>> getEvenIndexedStrings(List<String> names) {
        List<Indexed<String>> list = StreamUtils.zipWithIndex(names.stream())
            .filter(i -> i.getIndex() % 2 == 0)
            .collect(Collectors.toList());
        return list;
    }

    public static List<Indexed<String>> getOddIndexedStrings(List<String> names) {
        List<Indexed<String>> list = StreamUtils.zipWithIndex(names.stream())
            .filter(i -> i.getIndex() % 2 == 1)
            .collect(Collectors.toList());
        return list;
    }

    public static List<String> getOddIndexedStrings(String[] names) {
        List<String> oddIndexedNames = IntStream.range(0, names.length)
            .filter(i -> i % 2 == 1)
            .mapToObj(i -> names[i])
            .collect(Collectors.toList());
        return oddIndexedNames;
    }

    public static List<String> getOddIndexedStringsVersionTwo(String[] names) {
        List<String> oddIndexedNames = Stream.of(names)
            .zipWithIndex()
            .filter(tuple -> tuple._2 % 2 == 1)
            .map(tuple -> tuple._1)
            .toJavaList();
        return oddIndexedNames;
    }

    public static List<String> getEvenIndexedStringsUsingAtomicInteger(String[] names) {
        AtomicInteger index = new AtomicInteger(0);
        return Arrays.stream(names)
            .filter(name -> index.getAndIncrement() % 2 == 0)
            .collect(Collectors.toList());
    }

    public static List<String> getEvenIndexedStringsAtomicIntegerParallel(String[] names) {
        AtomicInteger index = new AtomicInteger(0);
        return Arrays.stream(names)
            .parallel()
            .filter(name -> index.getAndIncrement() % 2 == 0) .collect(Collectors.toList());
    }
}