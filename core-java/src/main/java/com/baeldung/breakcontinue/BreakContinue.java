package com.baeldung.breakcontinue;

/**
 * @author Santosh
 *
 */
public class BreakContinue {

    /**
     * @param args
     */
    public static void main(String[] args) {
        unlabeledBreak();
        unlabeledBreakMultipleLoops();
        labeledBreak();
        unlabeledContinue();
        labeledContinue();
    }

    private static void unlabeledBreak() {
        System.out.println("#### unlabeledBreak");
        String searchName = "Wilson";
        boolean foundName = false;
        String[] names = { "John", "Peter", "Robert", "Wilson", "Anthony", "Donald", "Richard" };

        for (String name : names) {
            if (name.equalsIgnoreCase(searchName)) {
                foundName = true;
                break;
            }
        }

        if (foundName) {
            System.out.println("Found the name in the array");
        } else {
            System.out.println(searchName + " could not be found in the array");
        }
    }

    private static void unlabeledBreakMultipleLoops() {
        System.out.println("#### unlabeledBreakMultipleLoops");
        String searchName = "Wilson";
        boolean foundName = false;
        String[][] names = { { "John", "Peter", "Robert", "Wilson" }, { "Anthony", "Donald", "Richard", "Arnold" }, { "Wilson", "Michael", "Stephen", "Ryan" } };

        for (String[] nameRow : names) {
            for (String name : nameRow) {
                if (name.equalsIgnoreCase(searchName)) {
                    foundName = true;
                    break;
                }
            }
            if (foundName) {
                System.out.println("Found the name in the array");
                foundName = false;
            } else {
                System.out.println(searchName + " could not be found in the array");
            }
        }
    }

    private static void labeledBreak() {
        System.out.println("#### labeledBreak");
        String searchName = "Wilson";
        boolean foundName = false;
        String[][] names = { { "John", "Peter", "Robert", "Wilson" }, { "Anthony", "Donald", "Richard", "Arnold" }, { "Wilson", "Michael", "Stephen", "Ryan" } };

        compare: 
        for (String[] nameRow : names) {
            for (String name : nameRow) {
                if (name.equalsIgnoreCase(searchName)) {
                    foundName = true;
                    break compare;
                }
            }
        }

        if (foundName) {
            System.out.println("Found the name in the array");
            foundName = false;
        } else {
            System.out.println(searchName + " could not be found in the array");
        }

    }

    private static void unlabeledContinue() {
        System.out.println("#### unlabeledContinue");
        String searchName = "Wilson";
        int totalMatches = 0;
        String[][] names = { { "John", "Wilson", "Robert", "Wilson" }, { "Anthony", "Donald", "Wilson", "Arnold" }, { "Wilson", "Michael", "Wilson", "Ryan" } };

        for (String[] nameRow : names) {
            for (String name : nameRow) {
                if (!name.equalsIgnoreCase(searchName)) {
                    continue;
                }

                totalMatches++;
            }
        }

        System.out.println("The name " + searchName + " was encountered " + totalMatches + " times");
    }

    private static void labeledContinue() {
        System.out.println("#### labeledContinue");
        String searchName = "Wilson";
        int rowNum = 0;
        String[][] names = { { "John", "Wilson", "Robert", "Wilson" }, { "Anthony", "Donald", "Wilson", "Arnold" }, { "Wilson", "Michael", "Wilson", "Ryan" } };

        compare: for (String[] nameRow : names) {
            rowNum++;
            for (String name : nameRow) {
                if (name.equalsIgnoreCase(searchName)) {
                    System.out.println("The name " + searchName + " is present in row number " + rowNum + ", hence skipping the current row.");
                    continue compare;
                }
            }
        }
    }

}
