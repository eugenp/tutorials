package com.baeldung.stream;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.codepoetics.protonpack.Indexed;
import com.codepoetics.protonpack.StreamUtils;
import com.google.common.collect.Streams;

import one.util.streamex.EntryStream;

public class StreamIndices {

    public static List<String> getEvenIndexedStrings(String[] names) {
        List<String> evenIndexedNames = IntStream.range(0, names.length)
            .filter(i -> i % 2 == 0)
            .mapToObj(i -> names[i])
            .collect(Collectors.toList());
        return evenIndexedNames;
    }

    public static List<String> getEvenIndexedStringsVersionTwo(List<String> names) {
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
        AtomicInteger index = new AtomicInteger();
        List<String> list = Arrays.stream(names)
            .filter(name -> index.getAndIncrement() % 2 == 1)
            .collect(Collectors.toList());
        return list;
    }
}
