package com.baeldung.stringIterator;

public class StringIterator {
    public static void main(String[] args) {
        String str = "Hello, Baeldung!";
        java8forEach(str);
    }
  
    public static void java8forEach(String str){
        str.chars().forEach(name -> {
            System.out.print((char) name);
        });
    }
}
