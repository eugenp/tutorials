package com.baeldung.skippingelements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SkippingElements {

    private SkippingElements() {
    }

    public static List<String> skipNthElementInListWithFilter(List<String> sourceList, int n) {
return IntStream.range(0, sourceList.size())
    .filter(s -> (s + 1) % n == 0)
    .mapToObj(sourceList::get)
    .collect(Collectors.toList());
    }

    public static List<String> skipNthElementInListWithIterate(List<String> sourceList, int n) {
int limit = sourceList.size() / n;
return IntStream.iterate(n - 1, i -> (i + n))
    .limit(limit)
    .mapToObj(sourceList::get)
    .collect(Collectors.toList());
    }

    public static List<String> skipNthElementInListWithSublist(List<String> sourceList, int n) {
int limit = sourceList.size() / n;
return Stream.iterate(sourceList, s -> s.subList(n, s.size()))
    .limit(limit)
    .map(s -> s.get(n - 1))
    .collect(Collectors.toList());
    }

    public static List<String> skipNthElementInListWithFor(List<String> sourceList, int n) {
List<String> result = new ArrayList<>();
for (int i = n - 1; i < sourceList.size(); i += n) {
    result.add(sourceList.get(i));
}
return result;
    }

    public static List<String> skipNthElementInListWithIterator(Stream<String> sourceStream, int n) {
List<String> result = new ArrayList<>();
final Iterator<String> iterator = sourceStream.iterator();
int count = 0;
while (iterator.hasNext()) {
    if (count % n == n - 1) {
        result.add(iterator.next());
    } else {
        iterator.next();
    }
    ++count;
}
return result;
    }

public static List<String> skipNthElementInStreamWithCollector(Stream<String> sourceStream, int n) {
    return sourceStream.collect(SkippingCollector.collector(n));
}

}
