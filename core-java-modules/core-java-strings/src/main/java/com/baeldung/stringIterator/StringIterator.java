package com.baeldung.stringIterator;

import java.text.*; 
import java.util.*;

public class StringIterator {

    public static String javaCharArray(String str){
        StringBuilder result = new StringBuilder();
        for (char c : str.toCharArray()) { 
            result.append(c);
        }
        return result.toString();
    }

    public static String javaforLoop(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            result.append(c);
        }
        return result.toString();
    }

    public static String java8forEach(String str){
        StringBuilder result = new StringBuilder();
        str.chars().forEach(name -> {
            result.append((char) name);
        });
        return result.toString();
    }

    public static String javaCharacterIterator(String str){
        StringBuilder result = new StringBuilder();
        CharacterIterator it = new StringCharacterIterator(str);  
        while (it.current() != CharacterIterator.DONE) { 
            result.append(it.current()); 
            it.next(); 
        }
        return result.toString();
    }
}
