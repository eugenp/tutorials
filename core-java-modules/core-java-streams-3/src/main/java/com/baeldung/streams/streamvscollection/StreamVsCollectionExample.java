package com.baeldung.streams.streamvscollection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamVsCollectionExample {
    
    static ArrayList<String> userNameSource = new ArrayList<>();
    
    static {
        userNameSource.add("john");
        userNameSource.add("smith");
        userNameSource.add("tom");
        userNameSource.add("rob");
        userNameSource.add("charlie");
        userNameSource.add("alfred"); 
    }
    
    public static Stream<String> userNames() {
        return userNameSource.stream();
    }
    
    public static List<String> userNameList() {
        return userNames().collect(Collectors.toList());
    }

    public static Stream<String> filterUserNames() {
        return userNames().filter(i -> i.length() >= 4);
    }
    
    public static Stream<String> sortUserNames() {
        return userNames().sorted();
    }
    
    public static Stream<String> limitUserNames() {
        return userNames().limit(3);
    }
    
    public static Stream<String> sortFilterLimitUserNames() {
        return filterUserNames().sorted().limit(3);
    }
    
    public static void printStream(Stream<String> stream) {
        stream.forEach(System.out::println);
    }
    
}
