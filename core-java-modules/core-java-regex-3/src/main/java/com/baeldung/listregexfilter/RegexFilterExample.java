package com.baeldung.listregexfilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RegexFilterExample {

    public List<String> filterUsingPatternAndPredicate() {
        List<String> fruits = List.of("apple", "banana", "cherry", "apricot", "avocado");

        Pattern pattern = Pattern.compile("^a.*");

        return fruits.stream()
          .filter(pattern.asPredicate())
          .toList();
    }

    public List<String> filterUsingStringMatches() {
        List<String> list = List.of("123", "abc", "456def", "789", "xyz");

        return list.stream()
          .filter(str -> str.matches("\\d+"))
          .toList();
    }

    public List<String> filterUsingPatternCompile() {
        List<String> numbers = List.of("one", "two", "three", "four", "five");
        List<String> startWithTList = new ArrayList<>();

        Pattern pattern = Pattern.compile("^t.*");

        for (String item : numbers) {
            Matcher matcher = pattern.matcher(item);
            if (matcher.matches())
                startWithTList.add(item);
        }

        return startWithTList;
    }

    public Map<Boolean, List<String>> filterUsingCollectorsPartitioningBy() {
        List<String> fruits = List.of("apple", "banana", "apricot", "berry");

        Pattern pattern = Pattern.compile("^a.*");

        return fruits.stream()
          .collect(Collectors.partitioningBy(pattern.asPredicate()));
    }

    public static void main(String[] args) {
        RegexFilterExample regexFilterExample = new RegexFilterExample();
        regexFilterExample.filterUsingPatternAndPredicate();
        regexFilterExample.filterUsingStringMatches();
        regexFilterExample.filterUsingPatternCompile();
        regexFilterExample.filterUsingCollectorsPartitioningBy();
    }
}
