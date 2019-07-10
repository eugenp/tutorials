package com.baeldung.reduce.application;

import com.baeldung.reduce.entities.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Warmup;

public class Application {

    public static void main(String[] args) throws Exception {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        int result1 = numbers.stream().reduce(0, (subtotal, element) -> subtotal + element);
        System.out.println(result1);

        int result2 = numbers.stream().reduce(0, Integer::sum);
        System.out.println(result2);

        List<String> letters = Arrays.asList("a", "b", "c", "d", "e");
        String result3 = letters.stream().reduce("", (partialString, element) -> partialString + element);
        System.out.println(result3);

        String result4 = letters.stream().reduce("", String::concat);
        System.out.println(result4);
        
        String result5 = letters.stream().reduce("", (partialString, element) -> partialString.toUpperCase() + element.toUpperCase());
        System.out.println(result5);
        
        List<User> users = Arrays.asList(new User("John", 30), new User("Julie", 35));
        int result6 = users.stream().reduce(0, (partialAgeResult, user) -> partialAgeResult + user.getAge(), Integer::sum);
        System.out.println(result6);

        String result7 = letters.parallelStream().reduce("", String::concat);
        System.out.println(result7);

        int result8 = users.parallelStream().reduce(0, (partialAgeResult, user) -> partialAgeResult + user.getAge(), Integer::sum);
        System.out.println(result8);

        org.openjdk.jmh.Main.main(args);
        
    }
    
    @Benchmark
    @Fork(value = 1, warmups = 2)
    @Warmup(iterations = 2) 
    @BenchmarkMode(Mode.AverageTime)
    public void executeReduceOnParallelizedStream() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i <= 1000000; i++) {
            userList.add(new User("John" + i, i));
        }
        userList.parallelStream().reduce(0, (partialAgeResult, user) -> partialAgeResult + user.getAge(), Integer::sum);
    }
    
    @Benchmark
    @Fork(value = 1, warmups = 2)
    @Warmup(iterations = 2) 
    @BenchmarkMode(Mode.AverageTime)
    public void executeReduceOnSequentialStream() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i <= 1000000; i++) {
            userList.add(new User("John" + i, i));
        }
        userList.stream().reduce(0, (partialAgeResult, user) -> partialAgeResult + user.getAge(), Integer::sum);
    }
}
