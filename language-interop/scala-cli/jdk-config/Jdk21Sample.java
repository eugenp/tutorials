//> using jvm zulu:21
package com.baeldung;
record Greet(String name){};
public class Jdk21Sample {
    public static void main(String args[]) {
        var greet = new Greet("Baeldung!");
        var greeting = "Hello, " + greet.name();
        System.out.println(greeting);
    }
}
