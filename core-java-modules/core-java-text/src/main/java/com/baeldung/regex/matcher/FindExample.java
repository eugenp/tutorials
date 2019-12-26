package com.baeldung.regex.matcher;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindExample {

    public static void main(String[] args) throws ParseException {

        Pattern stringPattern = Pattern.compile("\\d\\d\\d\\d");
        Matcher m = stringPattern.matcher("goodbye 2019 and welcome 2020");

        while (m.find()) {
            System.out.println("Start Index: " + m.start());
            System.out.println("Group: " + m.group());
            System.out.println("End Index: " + m.end());
        }
    }

}
