package com.baeldung.streamreduce.application;

import com.baeldung.streamreduce.entities.User;
import com.baeldung.streamreduce.utilities.NumberUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        int result1 = numbers.stream().reduce(0, (a, b) -> a + b);
        System.out.println(result1);
        
        int result2 = numbers.stream().reduce(0, Integer::sum);
        System.out.println(result2);

        List<String> letters = Arrays.asList("a", "b", "c", "d", "e");
        String result3 = letters.stream().reduce("", (a, b) -> a + b);
        System.out.println(result3);
        
        String result4 = letters.stream().reduce("", String::concat);
        System.out.println(result4);

        String result5 = letters.stream().reduce("", (a, b) -> a.toUpperCase() + b.toUpperCase());
        System.out.println(result5);
        
        List<User> users = Arrays.asList(new User("John", 30), new User("Julie", 35));
        int result6 = users.stream().reduce(0, (partialAgeResult, user) -> partialAgeResult + user.getAge(), Integer::sum);
        System.out.println(result6);
        
        String result7 = letters.parallelStream().reduce("", String::concat);
        System.out.println(result7);
        
        int result8 = users.parallelStream().reduce(0, (partialAgeResult, user) -> partialAgeResult + user.getAge(), Integer::sum);
        System.out.println(result8);
        
        List<User> userList = new ArrayList<>();
        for (int i = 0; i <= 1000000; i++) {
            userList.add(new User("John" + i, i));
        }
        
        long t1 = System.currentTimeMillis();
        int result9 = userList.stream().reduce(0, (partialAgeResult, user) -> partialAgeResult + user.getAge(), Integer::sum);
        long t2 = System.currentTimeMillis();
        System.out.println(result9);
        System.out.println("Sequential stream time: " + (t2 - t1) + "ms");
        
        long t3 = System.currentTimeMillis();
        int result10 = userList.parallelStream().reduce(0, (partialAgeResult, user) -> partialAgeResult + user.getAge(), Integer::sum);
        long t4 = System.currentTimeMillis();
        System.out.println(result10);
        System.out.println("Parallel stream time: " + (t4 - t3) + "ms");
        
        int result11 = NumberUtils.divideListElements(numbers, 1);
        System.out.println(result11);
        
        int result12 = NumberUtils.divideListElementsWithExtractedTryCatchBlock(numbers, 0);
        System.out.println(result12);
    }    
}
