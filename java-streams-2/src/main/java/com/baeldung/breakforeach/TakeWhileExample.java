package com.baeldung.breakforeach;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class TakeWhileExample {

    public static void takeWhileJava9() {
        Stream.of("cat", "dog", "elephant", "fox", "rabbit", "duck")
            .takeWhile(n -> n.length() % 2 != 0)
            .forEach(System.out::println); // cat, dog
    }

    public static void plainForLoopWithBreak() {
        List<String> list = asList("cat", "dog", "elephant", "fox", "rabbit", "duck");
        for (int i = 0; i < list.size(); i++) {
            String item = list.get(i);
            if (item.length() % 2 == 0) {
                break;
            }
            System.out.println(item);
        }
    }
}