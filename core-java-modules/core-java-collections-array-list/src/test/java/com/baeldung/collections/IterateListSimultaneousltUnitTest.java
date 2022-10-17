package com.baeldung.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.StreamUtils;

public class IterateListSimultaneousltUnitTest {

    List<String> names = List.of("John", "Nik", "Tom");
    List<Integer> numbers = List.of(1, 2, 3);

    @Test
    public void givenTwoLists_whenProcessByZipping_thenProcessedSimultaneously() {
        Stream<String> nameStream = names.stream();
        Stream<Integer> numberStream = numbers.stream();
        List<String> processedList = StreamUtils.zip(numberStream, nameStream,
            (numStr, nameStr) -> String.format("[Using Zip] Processing ArrayList Simultaneously : %d, %s", numStr, nameStr))
            .collect(Collectors.toList());
        Assertions.assertTrue(processedList.containsAll(
          List.of("[Using Zip] Processing ArrayList Simultaneously : 1, John",
                "[Using Zip] Processing ArrayList Simultaneously : 2, Nik",
                "[Using Zip] Processing ArrayList Simultaneously : 3, Tom")));
    }

    @Test
    public void givenTwoLists_whenIterateUsingIterator_thenProcessedSimultaneously() {
        Iterator nameIterator = names.iterator();
        Iterator numberIterator = numbers.iterator();
        List<String> processedList = new ArrayList<>();
        while (nameIterator.hasNext() && numberIterator.hasNext()) {
            String processedData = String.format("[Using Iterator] Processing ArrayList Simultaneously : %d, %s", numberIterator.next(), nameIterator.next());
            processedList.add(processedData);
        }
        Assertions.assertTrue(processedList.containsAll(
          List.of("[Using Iterator] Processing ArrayList Simultaneously : 1, John",
                "[Using Iterator] Processing ArrayList Simultaneously : 2, Nik",
                "[Using Iterator] Processing ArrayList Simultaneously : 3, Tom")));
    }

    @Test
    public void givenTwoLists_whenIterateUsingLoop_thenProcessedSimultaneously() {
        List<String> processedList = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            String processedData = String.format("[Using Loop] Processing ArrayList Simultaneously : %d, %s", numbers.get(i), names.get(i));
            processedList.add(processedData);
        }
        Assertions.assertTrue(processedList.containsAll(
          List.of("[Using Loop] Processing ArrayList Simultaneously : 1, John",
                "[Using Loop] Processing ArrayList Simultaneously : 2, Nik",
                "[Using Loop] Processing ArrayList Simultaneously : 3, Tom")));
    }

    @Test
    public void givenRecordList_whenIteratre_thenProcessedSimultaneously() {
        List<EncapData> list = List.of(new EncapData("John", 1),
                                new EncapData("Nik", 2),
                                new EncapData("Tom", 3));
        List<String> processedList = list.stream().map(l -> String.format("[Using Encapsulated Record] Processing ArrayList Simultaneously : %d, %s", l.getNumber(), l.getName()))
          .collect(Collectors.toList());
        Assertions.assertTrue(processedList.containsAll(
          List.of("[Using Encapsulated Record] Processing ArrayList Simultaneously : 1, John",
            "[Using Encapsulated Record] Processing ArrayList Simultaneously : 2, Nik",
            "[Using Encapsulated Record] Processing ArrayList Simultaneously : 3, Tom")));
    }

}

class EncapData {
    String name;
    int number;

    public EncapData(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}