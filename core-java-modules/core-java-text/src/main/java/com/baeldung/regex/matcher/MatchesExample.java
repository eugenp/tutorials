package com.baeldung.regex.matcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchesExample {

    public static void main(String[] args) {

        exampleOne();
        exampleTwo();
    }

    static void exampleOne() {

        Pattern stringPattern = Pattern.compile("\\d\\d\\d\\d");
        Matcher m = stringPattern.matcher("good bye 2019");
        System.out.println(m.matches());
    }

    static void exampleTwo() {

        Pattern stringPattern = Pattern.compile("\\d\\d\\d\\d");
        Matcher m = stringPattern.matcher("2019");
        System.out.println(m.matches());
        
        if (m.matches()) {
            System.out.println("Start Index: " + m.start());
            System.out.println("Group: " + m.group());
            System.out.println("End Index: " + m.end());
        }
    }
}
