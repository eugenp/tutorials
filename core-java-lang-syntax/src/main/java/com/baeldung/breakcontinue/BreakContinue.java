package com.baeldung.breakcontinue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Santosh
 *
 */

public class BreakContinue {

    public static int unlabeledBreak() {
        String searchName = "Wilson";
        int counter = 0;
        List<String> names = Arrays.asList("John", "Peter", "Robert", "Wilson", "Anthony", "Donald", "Richard");

        for (String name : names) {
            counter++;
            if (name.equalsIgnoreCase(searchName)) {
                break;
            }
        }

        return counter;
    }

    public static int unlabeledBreakNestedLoops() {
        String searchName = "Wilson";
        int counter = 0;
        Map<String, List<String>> nameMap = new HashMap<>();
        nameMap.put("Grade1", Arrays.asList("John", "Peter", "Robert", "Wilson"));
        nameMap.put("Grade2", Arrays.asList("Anthony", "Donald", "Richard", "Arnold"));
        nameMap.put("Grade3", Arrays.asList("Wilson", "Michael", "Stephen", "Ryan"));

        Iterator<Entry<String, List<String>>> iterator = nameMap.entrySet()
            .iterator();
        Entry<String, List<String>> entry = null;
        List<String> names = null;
        while (iterator.hasNext()) {
            entry = iterator.next();
            names = entry.getValue();
            for (String name : names) {
                if (name.equalsIgnoreCase(searchName)) {
                    counter++;
                    break;
                }
            }
        }

        return counter;
    }

    public static int labeledBreak() {
        String searchName = "Wilson";
        int counter = 0;
        Map<String, List<String>> nameMap = new HashMap<>();
        nameMap.put("Grade1", Arrays.asList("John", "Peter", "Robert", "Wilson"));
        nameMap.put("Grade2", Arrays.asList("Anthony", "Donald", "Richard", "Arnold"));
        nameMap.put("Grade3", Arrays.asList("Wilson", "Michael", "Stephen", "Ryan"));

        Iterator<Entry<String, List<String>>> iterator = nameMap.entrySet()
            .iterator();
        Entry<String, List<String>> entry = null;
        List<String> names = null;
        compare: 
        while (iterator.hasNext()) {
            entry = iterator.next();
            names = entry.getValue();
            for (String name : names) {
                if (name.equalsIgnoreCase(searchName)) {
                    counter++;
                    break compare;
                }
            }
        }

        return counter;
    }

    public static int unlabeledContinue() {
        String searchName = "Wilson";
        int counter = 0;
        Map<String, List<String>> nameMap = new HashMap<>();
        nameMap.put("Grade1", Arrays.asList("John", "Wilson", "Robert", "Wilson"));
        nameMap.put("Grade2", Arrays.asList("Anthony", "Donald", "Wilson", "Arnold"));
        nameMap.put("Grade3", Arrays.asList("Wilson", "Michael", "Wilson", "Ryan"));

        Iterator<Entry<String, List<String>>> iterator = nameMap.entrySet()
            .iterator();
        Entry<String, List<String>> entry = null;
        List<String> names = null;
        while (iterator.hasNext()) {
            entry = iterator.next();
            names = entry.getValue();
            for (String name : names) {
                if (!name.equalsIgnoreCase(searchName)) {
                    continue;
                }

                counter++;
            }
        }

        return counter;
    }

    public static int labeledContinue() {
        String searchName = "Wilson";
        int counter = 0;
        Map<String, List<String>> nameMap = new HashMap<>();
        nameMap.put("Grade1", Arrays.asList("John", "Wilson", "Robert", "Wilson"));
        nameMap.put("Grade2", Arrays.asList("Anthony", "Donald", "Wilson", "Arnold"));
        nameMap.put("Grade3", Arrays.asList("Wilson", "Michael", "Wilson", "Ryan"));

        Iterator<Entry<String, List<String>>> iterator = nameMap.entrySet()
            .iterator();
        Entry<String, List<String>> entry = null;
        List<String> names = null;
        compare: 
        while (iterator.hasNext()) {
            entry = iterator.next();
            names = entry.getValue();
            for (String name : names) {
                if (name.equalsIgnoreCase(searchName)) {
                    counter++;
                    continue compare;
                }
            }
        }

        return counter;
    }

}
