package com.baeldung.streams.streamvscollection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
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

    public static Set<String> userNameSet() {
        return userNames().collect(Collectors.toSet());
    }

    public static Map<String, String> userNameMap() {
        return userNames().collect(Collectors.toMap(u1 -> u1.toString(), u1 -> u1.toString()));
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

    public static void modifyList() {
        userNameSource.remove(2);
    }

    public static Map<String, String> modifyMap() {
        Map<String, String> userNameMap = userNameMap();
        userNameMap.put("bob", "bob");
        userNameMap.remove("alfred");

        return userNameMap;
    }

    public static void tryStreamTraversal() {
        Stream<String> userNameStream = userNames();
        userNameStream.forEach(System.out::println);
        
        try {
            userNameStream.forEach(System.out::println);
        } catch(IllegalStateException e) {
            System.out.println("stream has already been operated upon or closed");
        }
    }
    
    public static void main(String[] args) {
        System.out.println(userNameMap());
        System.out.println(modifyMap());      
        tryStreamTraversal();
        
        Set<String> set = userNames().collect(Collectors.toCollection(TreeSet::new));
        set.forEach(val -> System.out.println(val));  
        
    }
    
    
}
