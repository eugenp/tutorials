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

    /**
     * 预期值：4
     * @return
     */
    public static int unlabeledBreak() {
        String searchName = "Wilson";
        int counter = 0;
        List<String> names = Arrays.asList(
                "John",    "Peter",
                "Robert",  "Wilson",
                "Anthony", "Donald",
                "Richard"
        );

        for (String name : names) {
            counter++;
            if (name.equalsIgnoreCase(searchName)) {
                break;
            }
        }

        return counter;
    }

    private static Iterator<Entry<String, List<String>>> buildFistNameMapThenConvertIterator(){
        Map<String, List<String>> nameMap = new HashMap<>();
        nameMap.put("Grade1", Arrays.asList("John", "Peter", "Robert", "Wilson"));
        nameMap.put("Grade2", Arrays.asList("Anthony", "Donald", "Richard", "Arnold"));
        nameMap.put("Grade3", Arrays.asList("Wilson", "Michael", "Stephen", "Ryan"));

        return nameMap.entrySet().iterator();
    }


    /**
     * 预期值：2
     * @return
     */
    public static int unlabeledBreakNestedLoops() {
        String searchName = "Wilson";
        int counter = 0;
        Iterator<Entry<String, List<String>>> iterator = buildFistNameMapThenConvertIterator();
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


    /**
     * 预期值：1
     * @return
     */
    public static int labeledBreak() {
        String searchName = "Wilson";
        int counter = 0;
        Iterator<Entry<String, List<String>>> iterator = buildFistNameMapThenConvertIterator();
        Entry<String, List<String>> entry = null;
        compare:
        while (iterator.hasNext()) {
            entry = iterator.next();
            List<String> names = entry.getValue();
            for (String name : names) {
                if (name.equalsIgnoreCase(searchName)) {
                    counter++;
                    break compare;
                }
            }
        }

        return counter;
    }

    /**
     * 预期值：2
     * @return
     */
    public static int unlabeledContinue() {
        String searchName = "Wilson";
        int counter = 0;
        Iterator<Entry<String, List<String>>> iterator = buildFistNameMapThenConvertIterator();
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

    /**
     * 预期值：2
     * @return
     */
    public static int labeledContinue() {
        String searchName = "Wilson";
        int counter = 0;
        Iterator<Entry<String, List<String>>> iterator = buildFistNameMapThenConvertIterator();
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
