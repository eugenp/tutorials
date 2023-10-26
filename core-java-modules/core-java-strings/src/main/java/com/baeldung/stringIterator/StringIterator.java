package com.baeldung.stringIterator;

import java.text.*; 
import java.util.*;

public class StringIterator {
    public static void main(String[] args) {
        String str = "Hello, Baeldung!";
        javaforLoop(str);
        System.out.println();
        java8forEach(str);
        System.out.println();
        javaCharArray(str);
        System.out.println();
        javaRegexExp(str);
        System.out.println();
        javaCharacterIterator(str);
    }

    public static void javaCharArray(String str){
        for (char c : str.toCharArray()) { 
            System.out.print(c);
        }
    }

    public static void javaforLoop(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            System.out.print(c);
        }
    }

    public static void java8forEach(String str){
        str.chars().forEach(name -> {
            System.out.print((char) name);
        });
    }

    public static void javaRegexExp(String str){
        String[] characters = str.split(""); 
        for (String c : characters) { 
            System.out.print(c); 
        }
     }

    public static void javaCharacterIterator(String str){
        CharacterIterator it = new StringCharacterIterator(str);  
        while (it.current() != CharacterIterator.DONE) { 
            System.out.print(it.current()); 
            it.next(); 
        }
    }
}
